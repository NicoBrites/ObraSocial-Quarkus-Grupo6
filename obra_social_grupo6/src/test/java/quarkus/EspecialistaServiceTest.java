package quarkus;

import java.time.LocalTime;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.quarkus.test.junit.QuarkusTest;
import quarkus.dto.EspecialistaDto;
import quarkus.dto.EspecialistaRequest;
import quarkus.dto.mapper.EspecialistaMapper;
import quarkus.entity.Especialista;
import quarkus.entity.Ubicacion;
import quarkus.exception.UserNotFoundException;
import quarkus.repository.EspecialistaRepository;
import quarkus.service.impl.EspecialistaServiceImpl;




@QuarkusTest
public class EspecialistaServiceTest {

    @InjectMocks
	private EspecialistaServiceImpl especialistaServiceImpl;
	
	@Mock 
	private EspecialistaRepository especialistaRepository;

    @Mock 
	private EspecialistaMapper especialistaMapper;
    
    Especialista especialistaEntity;
    EspecialistaRequest especialistaUpdate;
	EspecialistaDto especialistaDto;
	
	private long randomNumber;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); 
        
        especialistaEntity = new Especialista();
        especialistaUpdate = new EspecialistaRequest("Carlos", "Cardiologo", LocalTime.of(9, 0), LocalTime.of(17, 0), new Ubicacion());
        especialistaDto = new EspecialistaDto("Carlos", "Cardiologo", LocalTime.of(9, 0), LocalTime.of(17, 0), new Ubicacion());
        
        Random random = new Random();
	    randomNumber = random.nextLong(1001);
    }

    @Test
    public void GetByIdTest(){
		//arrange             
        Optional<Especialista> expectedEspecialista = Optional.of(especialistaEntity);
      
        when(especialistaRepository.findByIdOptional(randomNumber)).thenReturn(expectedEspecialista);

        //act
        Optional<Especialista> actualEspecialista = especialistaServiceImpl.getByID(randomNumber);
        
        //assert
        assertEquals(expectedEspecialista, actualEspecialista);
       
    }

    @Test
    public void GetByIdTestReturnOptionalEmpty() {
		//arrange
		//act	
		Optional<Especialista> actualEspecialista = especialistaServiceImpl.getByID(randomNumber);
		//assert
	    assertEquals(Optional.empty(), actualEspecialista);
       
    }

    @Test
	public void SaveTest() {				
		//arrange
        EspecialistaRequest especialistaRequest = especialistaUpdate;
        Especialista expectedEntity = especialistaEntity;
        EspecialistaDto expectedDto = especialistaDto;
        
        when(especialistaMapper.RequestToEntity(especialistaRequest)).thenReturn(expectedEntity);
        
        when(especialistaMapper.EntityToDto(expectedEntity)).thenReturn(expectedDto);

        //act
        EspecialistaDto actualDto = especialistaServiceImpl.save(especialistaRequest);

        //assert
        verify(especialistaRepository).persist(expectedEntity);
        
        assertEquals(expectedDto, actualDto);
		
	}

    @Test
	public void DeleteTest() {
        //arrange
        when(especialistaRepository.findByIdOptional(randomNumber)).thenReturn(Optional.of(especialistaEntity));

        //act
        especialistaServiceImpl.delete(randomNumber);

        //assert
        verify(especialistaRepository).persist(especialistaEntity);

        assertTrue(especialistaEntity.getEstaBorrado());
               
	}

    @Test
	public void DeleteTestReturnEspecialistaNoEncontrado() {
		//arrange
        //act
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            especialistaServiceImpl.delete(randomNumber);
        });

        //assert
        assertEquals("Especialista no encontrado", exception.getMessage()); 
               
	}

    @Test
    public void UpdateTest() {
       //arrange
       especialistaEntity.setEstaBorrado(false);
              
       Especialista expectedEspecialista = especialistaEntity;
       Optional<Especialista> optionalEspecialista = Optional.of(expectedEspecialista);
       
       EspecialistaDto expectedDto = especialistaDto;
       EspecialistaRequest especialistaRequest = especialistaUpdate;
       
       when(especialistaRepository.findByIdOptional(randomNumber)).thenReturn(optionalEspecialista);
       
       when(especialistaMapper.EntityToDto(expectedEspecialista)).thenReturn(expectedDto);

       //act
       EspecialistaDto actualDto = especialistaServiceImpl.update(especialistaRequest, randomNumber);
       
       //assert
       verify(especialistaRepository).persist(expectedEspecialista);
       
       assertEquals(expectedDto, actualDto);
       
   }

   @Test
    public void UpdateTest_WhenEspecialistaNoExiste() {
    	//arrange     
        when(especialistaRepository.findByIdOptional(randomNumber)).thenReturn(Optional.empty());
        //act y assert
        assertThrows(UserNotFoundException.class, () -> especialistaServiceImpl.update(especialistaUpdate, randomNumber));

        verify(especialistaRepository, never()).persist(any(Especialista.class));
    }

    @Test
    public void UpdateTest_whenEspecialistaEstaBorrado() {
        //arrange
        especialistaEntity.setEstaBorrado(true);
        
        when(especialistaRepository.findByIdOptional(randomNumber)).thenReturn(Optional.of(especialistaEntity));
        //act y assert
        assertThrows(UserNotFoundException.class, () -> especialistaServiceImpl.update(especialistaUpdate, randomNumber));

        verify(especialistaRepository, never()).persist(any(Especialista.class));
    }

    @Test
    public void UpdateTestValidarHorarioTestThrowTurnoException() {
    	//arrange
        especialistaEntity.setEstaBorrado(true);
        
        when(especialistaRepository.findByIdOptional(randomNumber)).thenReturn(Optional.of(especialistaEntity));
        //act y assert
        assertThrows(UserNotFoundException.class, () -> especialistaServiceImpl.update(especialistaUpdate, randomNumber));

        verify(especialistaRepository, never()).persist(any(Especialista.class));
    }
}
