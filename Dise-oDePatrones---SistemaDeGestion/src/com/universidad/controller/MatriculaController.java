package com.universidad.controller;

import com.universidad.dao.MatriculaDAO;
import com.universidad.model.Curso;
import com.universidad.model.Estudiante;
import com.universidad.model.PlanEstudio;
import com.universidad.patrones.Comportamiento.command.Command;
import com.universidad.patrones.Comportamiento.command.RegistrarMatriculaCommand;
import com.universidad.patrones.Comportamiento.memento.HistorialMatriculaCaretaker;
import com.universidad.patrones.Comportamiento.memento.MatriculaMemento;
import com.universidad.patrones.Comportamiento.observer.AlumnoObserver;
import com.universidad.patrones.Comportamiento.observer.CanalNotificacionSubject;
import com.universidad.patrones.Comportamiento.state.MatriculaContext;
import com.universidad.patrones.Creacionales.Factorymethod.Modalidad;
import com.universidad.patrones.Creacionales.Factorymethod.ModalidadFactory;
import com.universidad.patrones.Creacionales.Factorymethod.PresencialFactory;
import com.universidad.patrones.Creacionales.Factorymethod.VirtualFactory;
import com.universidad.patrones.Creacionales.Singleton.AuditoriaManager;
import com.universidad.patrones.Creacionales.Builder.PlanEstudioBuilder;
import com.universidad.patrones.Estructurales.adapter.IPagoService;
import com.universidad.patrones.Estructurales.adapter.PagoAdapter;
import com.universidad.patrones.Estructurales.adapter.SistemaPagosExterno;
import com.universidad.patrones.Estructurales.facade.MatriculaFacade;
import com.universidad.patrones.Estructurales.proxy.HistorialServiceProxy;
import com.universidad.patrones.Estructurales.proxy.IHistorialService;
import com.universidad.patrones.solid.INotificacionService;
import com.universidad.service.AuditoriaService;
import com.universidad.service.MatriculaService;
import com.universidad.service.ValidadorMatricula;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MatriculaController {
    private final MatriculaFacade matriculaFacade;
    private final MatriculaService matriculaService;
    private final IPagoService pagoService;
    private final MatriculaContext estadoContext;
    private final HistorialMatriculaCaretaker caretaker;
    private final CanalNotificacionSubject canalNotificacion;
    private final MatriculaDAO matriculaDAO = new MatriculaDAO();

    private static final List<Object[]> listaMatriculas = new ArrayList<>();
    private static final List<Object[]> listaAuditoria = new ArrayList<>();

    public MatriculaController() {
        INotificacionService notificador = (destinatario, mensaje) -> 
            System.out.println("[NOTIFICACIÓN UI] Para " + destinatario + ": " + mensaje);
        
        this.matriculaFacade = new MatriculaFacade(notificador);
        this.matriculaService = new MatriculaService(new ValidadorMatricula(), new AuditoriaService(), notificador);
        this.pagoService = new PagoAdapter(new SistemaPagosExterno());
        this.estadoContext = new MatriculaContext();
        this.caretaker = new HistorialMatriculaCaretaker();
        this.canalNotificacion = new CanalNotificacionSubject();
    }

    public void registrarInicioSesion(String usuario, String rol) {
        AuditoriaManager.getInstancia().registrarEvento(usuario, "Inicio de sesión con rol: " + rol);
        listaAuditoria.add(new Object[]{LocalTime.now().withNano(0).toString(), usuario, "LOGIN", "Acceso con rol " + rol});
    }

    public boolean registrarMatricula(String codigoEst, String nombreEst, boolean tieneDeuda, String nombreCurso, int vacantes) {
        Estudiante est = new Estudiante(1, nombreEst, codigoEst, tieneDeuda);
        Curso curso = new Curso(101, nombreCurso, 4, vacantes);

        pagoService.procesarPagoMatricula(codigoEst, 350.00);
        
        Command cmd = new RegistrarMatriculaCommand(matriculaService, est, curso);
        cmd.execute();

        boolean exito = matriculaFacade.procesarMatriculaCompleta(est, curso);
        if (exito) {
            matriculaDAO.registrarMatriculaBD(codigoEst, nombreEst, nombreCurso, 350.00, "CONFIRMADA");
            listaMatriculas.add(new Object[]{codigoEst, nombreEst, nombreCurso, "S/ 350.00", "CONFIRMADA"});
            listaAuditoria.add(new Object[]{LocalTime.now().withNano(0).toString(), codigoEst, "MATRÍCULA", "Inscrito en " + nombreCurso});
        }
        return exito;
    }

    public String programarCursoYPlan(String tipoModalidad, String carrera, int creditos, boolean tesis) {
        ModalidadFactory factory = "Virtual".equalsIgnoreCase(tipoModalidad) 
                ? new VirtualFactory() 
                : new PresencialFactory();
        Modalidad modalidad = factory.crearModalidad();

        PlanEstudio plan = new PlanEstudioBuilder(carrera, 2026)
                .setCreditosTotales(creditos)
                .setRequiereTesis(tesis)
                .build();

        return "Modalidad asignada: " + modalidad.getNombre() + 
               "\nPlan construido: " + plan.getCarrera() + " (" + plan.getCreditosTotales() + " créditos, Tesis: " + plan.isRequiereTesis() + ")";
    }

    public List<String> consultarHistorial(String rolUsuario, String codigoEstudiante) {
        IHistorialService proxy = new HistorialServiceProxy(rolUsuario);
        return proxy.consultarHistorial(codigoEstudiante);
    }

    public String obtenerEstadoActual() {
        return estadoContext.getEstadoNombre();
    }

    public String avanzarEstadoYRespaldar(String codigoAlumno) {
        caretaker.guardarSnapshot(new MatriculaMemento(estadoContext.getEstadoNombre()));
        estadoContext.avanzarEstado();
        String nuevoEstado = estadoContext.getEstadoNombre();

        canalNotificacion.agregarObservador(new AlumnoObserver(codigoAlumno));
        canalNotificacion.notificarTodos("El estado de su matrícula cambió a: " + nuevoEstado);

        listaAuditoria.add(new Object[]{LocalTime.now().withNano(0).toString(), codigoAlumno, "ESTADO", "Cambió a " + nuevoEstado});
        return nuevoEstado;
    }

    public List<Object[]> obtenerReporteMatriculas() {
        List<Object[]> desdeBD = matriculaDAO.listarMatriculasBD();
        return desdeBD.isEmpty() ? listaMatriculas : desdeBD;
    }

    public List<Object[]> obtenerReporteAuditoria() {
        return listaAuditoria;
    }
}