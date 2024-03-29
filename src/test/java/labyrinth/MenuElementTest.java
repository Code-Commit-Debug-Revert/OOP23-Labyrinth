package labyrinth;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ccdr.labyrinth.menu.element.MenuButtonElement;
import com.ccdr.labyrinth.menu.element.MenuChoiceElement;
import com.ccdr.labyrinth.menu.element.MenuElement;
import com.ccdr.labyrinth.menu.element.MenuListElement;
import com.ccdr.labyrinth.menu.element.MenuTextElement;

/**
 * Class that contains all test cases for MenuElements.
 */
final class MenuElementTest {

    private MenuElement tree;
    private boolean signal;

    @BeforeEach
    void setupMenuTree() {
        tree = new MenuListElement("Root",
            new MenuTextElement("Text element", "Description for text element"),
            new MenuChoiceElement<>("Choice box", List.of("a", "b", "c", "d")),
            new MenuListElement("InnerList",
                new MenuTextElement("Clickable Text", null),
                new MenuButtonElement("Clickable Button", () -> signal = true)),
            new MenuButtonElement("Fake Play", () -> signal = true)
        );
        signal = false;
    }

    @Test
    void movementUp() {
        //menu list should not wrap around when scrolling
        final MenuListElement root = (MenuListElement) tree;
        for (int i = 0; i < 10; i++) {
            tree.up();
        }
        assertEquals(0, root.getIndex());
    }

    @Test
    void movementDown() {
        //menu list should not wrap around when scrolling
        final MenuListElement root = (MenuListElement) tree;
        for (int i = 0; i < 10; i++) {
            tree.down();
        }
        assertEquals(3, root.getIndex());
    }

    @Test
    void movementEnterText() {
        //text element should return back to the parent once selected
        assertEquals(tree, tree.nextState().nextState());
    }

    @Test
    void movementEnterChoice() {
        tree.down();
        final MenuElement elm = tree.nextState();
        if (elm instanceof MenuChoiceElement<?>) {
            final MenuChoiceElement<?> choice = (MenuChoiceElement<?>) elm;
            //pass only if the chosen object is 'c'
            choice.action(obj -> signal = "c".equals(obj));
            choice.down();
            choice.down();
            final MenuElement maybeRoot = choice.nextState();
            assertTrue(signal);
            assertEquals(tree, maybeRoot);
        } else {
            fail("Wrong object type selected in menu");
        }
    }

    @Test
    void movementEnterList() {
        tree.down();
        tree.down();
        //if it's a list, then this should not be the same as root by definition
        assertNotEquals(tree, tree.nextState().nextState());
        assertEquals(tree.nextState(), tree.nextState().nextState().nextState());
    }

    @Test
    void movementEnterButton() {
        tree.down();
        tree.down();
        tree.down();
        final MenuElement button = tree.nextState();
        //if it's a button, then these should throw an exception
        assertThrowsExactly(IllegalCallerException.class, () -> {
            button.nextState();
        });

        assertThrowsExactly(IllegalCallerException.class, () -> {
            button.up();
        });

        assertThrowsExactly(IllegalCallerException.class, () -> {
            button.down();
        });

        //this must be fine to call on the button object
        button.immediate();
        assertTrue(signal);
    }


}
