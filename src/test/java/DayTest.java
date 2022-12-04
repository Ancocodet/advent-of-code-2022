import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Stream;

public class DayTest {

    @ParameterizedTest
    @MethodSource("getDays")
    public void testDay(IAdventDay adventDay){
        AInputData inputData = adventDay.getClass().getAnnotation(AInputData.class);
        Long[] outputs = readOutputs(inputData.day());

        assert outputs != null;

        String part1 = adventDay.part1(readFromFile("input/day" + inputData.day() + "-input"));
        try {
            assert Long.parseLong(part1) == outputs[0];
        }catch (NumberFormatException ignored){}

        String part2 = adventDay.part2(readFromFile("input/day" + inputData.day() + "-input"));
        try {
            assert Long.parseLong(part2) == outputs[1];
        }catch (NumberFormatException ignored){}
    }

    static Stream<IAdventDay> getDays(){
        Reflections reflections = new Reflections("de.ancozockt.advent.days");
        return reflections.getTypesAnnotatedWith(AInputData.class).stream().map(aClass -> (IAdventDay) createNewInstanceOfClass(aClass));
    }

    private Long[] readOutputs(int day){
        ArrayList<Long> outputs = new ArrayList<>();

        BufferedReader reader = readFromFile("output/day" + day + "-output");
        String line;
        try {
            while ((line = reader.readLine()) != null){
                outputs.add(Long.parseLong(line));
            }
        }catch (IOException ignored) { }

        return outputs.toArray(new Long[]{});
    }

    private BufferedReader readFromFile(String fileName){
        InputStream ioStream = getClass()
                .getClassLoader()
                .getResourceAsStream(fileName);

        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }

        return new BufferedReader(new InputStreamReader(ioStream));
    }

    private static <T> T createNewInstanceOfClass(Class<T> someClass) {
        try {
            return someClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }

}
