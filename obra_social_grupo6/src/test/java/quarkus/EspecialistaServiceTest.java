package quarkus;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.quarkus.test.junit.QuarkusTest;
import quarkus.entity.Especialista;
import quarkus.repository.EspecialistaRepository;
import quarkus.service.impl.EspecialistaServiceImpl;




@QuarkusTest
public class EspecialistaServiceTest {

    @InjectMocks
	private EspecialistaServiceImpl especialistaServiceImpl;
	
	@Mock 
	private EspecialistaRepository especialistaRepository;
    
    Especialista especialistaEntity;
	
	private long randomNumber;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); 
        
        especialistaEntity = new Especialista();
        
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
}
