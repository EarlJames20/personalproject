import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

class FlashCardTest {

    @Test
    public void testSetter_setsProperly() throws NoSuchFieldException, IllegalAccessException {
        //given
        final FlashCard card = new FlashCard(null, null);  
        
        //when
        card.setFront("test");

        //then
        final Field field = card.getClass().getDeclaredField("test");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(card), "test");
    }
    
    @Test
    public void testGetter_getsValue() throws NoSuchFieldException, IllegalAccessException {
        //given
    	final FlashCard card = new FlashCard(null, null);  
        final Field field = card.getClass().getDeclaredField("test");
        field.setAccessible(true);
        field.set(card, "test");

        //when
        final String result = card.getBack();

        //then
        assertEquals("Field wasn't retrieved properly", result, "test");
    }

}
