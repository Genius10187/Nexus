package com.meti.lib.util;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.meti.lib.util.Tests.directory;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/26/2017
 */
class UtilityTest {

    @Test
    void toCharObjectArray() {
        char[] test = new char[]{'a', 'b', 'c'};
        Character[] result = new Character[]{'a', 'b', 'c'};
        assertArrayEquals(result, Utility.toCharObjectArray(test));
    }

    @Test
    void scan() throws IOException {
        assertTrue(directory.mkdirs());

        File test1 = new File(directory, "test1.txt");
        assertTrue(test1.createNewFile());

        File test2 = new File(directory, "test2.png");
        assertTrue(test2.createNewFile());

        List<File> files1 = Utility.scan(directory);
        assertTrue(files1.size() == 3 && files1.containsAll(Arrays.asList(
                directory,
                new File(directory, "test1.txt"),
                new File(directory, "test2.png")
        )));

        List<File> files2 = Utility.scan(directory, "txt");
        assertTrue(files2.size() == 2 && files2.containsAll(Arrays.asList(
                directory,
                new File(directory, "test1.txt")
        )));

        assertTrue(test1.delete());
        assertTrue(test2.delete());
        assertTrue(directory.delete());
    }

    @Test
    void getExtension() throws IOException {
        assertTrue(directory.mkdirs());

        File test = new File(directory, "test.txt");
        assertTrue(test.createNewFile());

        String ext = Utility.getExtension(test);
        assertEquals("txt", ext);

        assertTrue(test.delete());
        assertTrue(directory.delete());
    }

    @Test
    void assertNullParameters() {
        Utility.assertNullParameters("1", 2);

        assertThrows(NullPointerException.class, () -> Utility.assertNullParameters(null, "test"));
    }

    @Test
    void load() {
        //can't test FX in non-FX thread...
        /*String code = "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<?import javafx.scene.layout.*?>\n" +
                "\n" +
                "<AnchorPane xmlns=\"http://javafx.com/javafx\"\n" +
                "            xmlns:fx=\"http://javafx.com/fxml\"\n" +
                "            fx:controller=\"com.meti.UtilityTest.TestController\"\n" +
                "            prefHeight=\"400.0\" prefWidth=\"600.0\">\n" +
                "\n" +
                "</AnchorPane>\n";

        PipedInputStream pipedInputStream = new PipedInputStream();
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        pipedOutputStream.connect(pipedInputStream);

        PrintWriter writer = new PrintWriter(pipedOutputStream);
        writer.print(code);
        writer.flush();

        Source<InputStream, OutputStream> source = Sources.createBasicSource(pipedInputStream, pipedOutputStream);
        Stage stage = new Stage();
        FXMLBundle<?> bundle = Utility.load(source, stage, EnumSet.of(Utility.FXML.LOAD_STAGE));

        assertNotNull(bundle);
        assertNotNull(bundle.getController());
        assertTrue(TestController.class.isAssignableFrom(bundle.getController().getClass()));

        assertTrue(stage.isShowing());

        stage.close();

        pipedInputStream.close();
        pipedOutputStream.close();*/
    }

    @Test
    void load1() {
    }

    @Test
    void load2() {
    }

    @Test
    void load3() {
    }

    @Test
    void load4() {
    }

    public class TestController {
    }
}