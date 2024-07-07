//package quarkus;
//
//import org.eclipse.microprofile.jwt.JsonWebToken;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import quarkus.dto.TurnoDto;
//import quarkus.dto.TurnoRequest;
//import quarkus.entity.Especialista;
//import quarkus.entity.Turno;
//import quarkus.entity.Usuario;
//import quarkus.exception.TurnoException;
//import quarkus.exception.UserNotFoundException;
//import quarkus.repository.TurnoRepository;
//import quarkus.service.impl.EspecialistaServiceImpl;
//import quarkus.service.impl.TurnoServiceImpl;
//import quarkus.service.impl.UsuarioServiceImpl;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class TurnoServiceImplTest {
//
//    @Mock
//    TurnoRepository turnoRepository;
//
//    @Mock
//    UsuarioServiceImpl usuarioService;
//
//    @Mock
//    EspecialistaServiceImpl especialistaService;
//
//    @Mock
//    JsonWebToken jwt;
//
//    @InjectMocks
//    TurnoSevice turnoService;
//
//    Turno turno;
//    Especialista especialista;
//    Usuario paciente;
//    TurnoRequest turnoRequest;
//
//    @BeforeEach
//    void setUp() {
//
//        paciente = Usuario.builder()
//                .username("testuser")
//                .password("password123")
//                .numeroAfiliado(2324324)
//                .nombre("test")
//                .apellido("test1")
//                .direccion("123 Main St")
//                .rol("PACIENTE")
//                .build();
//
//        paciente.setId(1L);
//
//        especialista = Especialista.builder()
//                .especialidad("Cardiologia")
//                .nombre("Juan")
//                .horarioEntrada(LocalTime.of(8, 0))
//                .horarioSalida(LocalTime.of(17, 0))
//                .build();
//
//        especialista.id = 1L;
//
//        turno = new Turno();
//        turno.setId(1L);
//        turno.setPaciente(paciente);
//        turno.setEspecialista(especialista);
//        turno.setFecha(LocalDate.of(2024, 6, 17));
//        turno.setHora(LocalTime.of(10, 0));
//
//        paciente.setTurnos(List.of(turno));
//
//        turnoRequest = new TurnoRequest(
//                1L, 1L, LocalDate.of(2024, 6, 18), LocalTime.of(12, 0), "Consulta");
//
//    }
//
//    @Test
//    void testCreateTurnoSuccessful() {
//
//        when(usuarioService.findById(turnoRequest.pacienteId())).thenReturn(Optional.of(paciente));
//        when(especialistaService.getByID(turnoRequest.especialistaId())).thenReturn(Optional.of(especialista));
//        doNothing().when(turnoRepository).persist(any(Turno.class));
//
//        TurnoDto turnoDto = turnoService.createTurno(turnoRequest);
//
//        assertNotNull(turnoDto);
//        verify(usuarioService, times(1)).findById(turnoRequest.pacienteId());
//        verify(especialistaService, times(1)).getByID(turnoRequest.especialistaId());
//        verify(turnoRepository, times(1)).persist(any(Turno.class));
//        assertEquals(turnoDto.especialistaId(), turnoRequest.especialistaId());
//        assertEquals(turnoDto.pacienteId(), turnoRequest.pacienteId());
//        assertEquals(turnoDto.fecha(), turnoRequest.fecha());
//        assertEquals(turnoDto.hora(), turnoRequest.hora());
//        assertEquals(turnoDto.motivoConsulta(), turnoRequest.motivoConsulta());
//    }
//
//    @Test
//    void testCreateTurno_UserNotFound() {
//        TurnoRequest turnoRequest = new TurnoRequest(
//                1L, 1L, LocalDate.now().plusDays(1),
//                LocalTime.of(10, 0), "Consulta");
//
//        when(usuarioService.findById(turnoRequest.pacienteId())).thenReturn(Optional.empty());
//
//        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
//            turnoService.createTurno(turnoRequest);
//        });
//
//        assertEquals("Paciente no encontrado", exception.getMessage());
//    }
//
//    @Test
//    void testCreateTurno_SpecialistNotFound() {
//        TurnoRequest turnoRequest = new TurnoRequest(
//                1L, 1L, LocalDate.now().plusDays(1),
//                LocalTime.of(10, 0), "Consulta");
//
//        when(usuarioService.findById(turnoRequest.pacienteId())).thenReturn(Optional.of(paciente));
//        when(especialistaService.getByID(turnoRequest.especialistaId())).thenReturn(Optional.empty());
//
//        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
//            turnoService.createTurno(turnoRequest);
//        });
//
//        assertEquals("No se encontro especialista", exception.getMessage());
//    }
//
//    @Test
//    void testCreateTurno_HasExistingTurno() {
//
//        TurnoRequest nuevoTurnoRequest = new TurnoRequest(
//                1L, 1L,
//                LocalDate.of(2024, 6, 17),
//                LocalTime.of(10, 0), "Consulta");
//
//        when(usuarioService.findById(turnoRequest.pacienteId())).thenReturn(Optional.of(paciente));
//        when(especialistaService.getByID(turnoRequest.especialistaId())).thenReturn(Optional.of(especialista));
//        assertThrows(TurnoException.class, () -> turnoService.createTurno(nuevoTurnoRequest));
//
//    }
//
//    @Test
//    void testUpdateTurnoSuccessful() {
//
//        when(turnoRepository.findByIdOptional(1L)).thenReturn(Optional.of(turno));
//        when(especialistaService.getByID(turnoRequest.especialistaId())).thenReturn(Optional.of(especialista));
//        when(jwt.getClaim("upn")).thenReturn("testuser");
//        doNothing().when(turnoRepository).persist(any(Turno.class));
//
//        TurnoDto turnoDto = turnoService.updateTurno(turnoRequest, 1L);
//
//        assertNotNull(turnoDto);
//        verify(turnoRepository, times(1)).findByIdOptional(1L);
//        verify(especialistaService, times(1)).getByID(turnoRequest.especialistaId());
//        verify(turnoRepository, times(1)).persist(any(Turno.class));
//    }
//    /*
//     * @Test
//     * void testGetAllByUserId() {
//     * Long userId = 1L;
//     *
//     * when(usuarioService.findById(userId)).thenReturn(Optional.of(paciente));
//     * when(jwt.getClaim("upn")).thenReturn("testuser");
//     * when(turnoRepository.findAllByUserId(userId)).thenReturn(List.of());
//     *
//     * List<TurnoDto> turnos = turnoService.getAllByUserId(userId);
//     *
//     * assertNotNull(turnos);
//     * verify(usuarioService, times(1)).findById(userId);
//     * verify(turnoRepository, times(1)).findAllByUserId(userId);
//     * }
//     */
//
//    @Test
//    void testDeleteTurno() {
//
//        Long turnoId = 1L;
//
//        when(turnoRepository.findByIdOptional(turnoId)).thenReturn(Optional.of(turno));
//        when(jwt.getClaim("upn")).thenReturn("testuser");
//        doNothing().when(turnoRepository).delete(any());
//
//        turnoService.deleteTurno(turnoId);
//
//        verify(turnoRepository, times(1)).findByIdOptional(turnoId);
//        verify(turnoRepository, times(1)).delete(any());
//    }
//
//    @Test
//    void testValidarFechaYHora_valid() {
//        assertDoesNotThrow(() -> {
//            turnoService.validarFechaYHora(especialista, paciente, turnoRequest);
//        });
//    }
//
//    @Test
//    void testValidarFechaYHora_pastDate() {
//        TurnoRequest turnoRequestPast = new TurnoRequest(
//                1L, 1L,
//                LocalDate.of(2023, 6, 16),
//                LocalTime.of(10, 0), "Consulta");
//
//        assertThrows(TurnoException.class,
//                () -> turnoService.validarFechaYHora(especialista, paciente, turnoRequestPast));
//    }
//
//    @Test
//    void testValidarFechaYHora_weekEnd() {
//        TurnoRequest turnoRequestWeekEnd = new TurnoRequest(
//                1L, 1L,
//                LocalDate.of(2024, 6, 22),
//                LocalTime.of(10, 0), "Consulta");
//
//        assertThrows(TurnoException.class,
//                () -> turnoService.validarFechaYHora(especialista, paciente, turnoRequestWeekEnd));
//    }
//
//    @Test
//    void testValidarFechaYHora_invalidHour() {
//        TurnoRequest turnoRequestInvalidHour = new TurnoRequest(
//                1L,
//                LocalDate.of(2024, 6, 18),
//                LocalTime.of(7, 0), "Consulta");
//
//        assertThrows(TurnoException.class,
//                () -> turnoService);
//    }
//
//}