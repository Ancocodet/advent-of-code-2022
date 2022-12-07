import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Stream;

@Slf4j
public class DayTest {

    @ParameterizedTest
    @MethodSource("getDays")
    public void testDay(IAdventDay adventDay){
        AInputData inputData = adventDay.getClass().getAnnotation(AInputData.class);

        System.out.println("======= Testing Day: " + inputData.day() + " =======");

        String[] outputs = readOutputs(inputData.day());

        assert outputs != null;
        System.out.println("Output does exist: ✓");

        try {
            String part1 = adventDay.part1(readFromFile("input/day" + inputData.day() + "-input"));
            try {
                long answer = Long.parseLong(part1);
                long expected = Long.parseLong(outputs[0]);

                assert answer == expected;
            }catch (NumberFormatException exception){
                assert part1.equals(outputs[0]);
            }
            System.out.println("Part-1: ✓");
        }catch (NullPointerException exception){ }


        try{
            String part2 = adventDay.part2(readFromFile("input/day" + inputData.day() + "-input"));
            try {
                long answer = Long.parseLong(part2);
                long expected = Long.parseLong(outputs[1]);

                assert answer == expected;
            }catch (NumberFormatException exception){
                assert part2.equals(outputs[1]);
            }
            System.out.println("Part-2: ✓");
        }catch (NullPointerException ignored){ }
    }

    static Stream<IAdventDay> getDays(){
        Reflections reflections = new Reflections("de.ancozockt.advent.days");
        return reflections.getTypesAnnotatedWith(AInputData.class).stream().map(aClass -> (IAdventDay) createNewInstanceOfClass(aClass));
    }

    private String[] readOutputs(int day){
        ArrayList<String> outputs = new ArrayList<>();

        BufferedReader reader = readFromFile("output/day" + day + "-output");
        String line;
        try {
            while ((line = reader.readLine()) != null){
                outputs.add(line);
            }
        }catch (IOException ignored) { }

        return outputs.toArray(new String[]{});
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
