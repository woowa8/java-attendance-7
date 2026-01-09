package attendance.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputParser {
    public List<Integer> parse(String input) {
        String[] words = input.split(":");

        for (String word : words) {
            if(!word.matches("[0-9]+")){
                throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
            }
        }

        int hour = Integer.parseInt(words[0]);
        int minute = Integer.parseInt(words[1]);

        if(0 > hour || hour > 23){
            throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
        }

        if(0 > minute|| minute > 59){
            throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
        }

        List<Integer> times = new ArrayList<>();
        times.add(hour);
        times.add(minute);

        return times;
    }
}
