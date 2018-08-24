package top.pppppap.train;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author pppppap
 * @since 2018-06-20 下午 20:23
 */

public class StationInfo {

    public static List<Station> stations = new ArrayList<Station>();

    static {
        File file = new File(StationInfo.class.getClassLoader().getResource("station.txt").getPath());
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "GBK");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] s1 = line.split("@");
                for (String i : s1) {
                    if (i.length()>3) {
                        String[] s = i.split("\\|");
                        Station station = new Station();
                        station.setShortSpell1(s[0]);
                        station.setName(s[1]);
                        station.setShorthand(s[2]);
                        station.setLongSpell(s[3]);
                        station.setShortSpell2(s[4]);
                        station.setNo(s[5]);
                        stations.add(station);
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("读取station文件时不支持的文件编码");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("读取station文件时不存在");
        } catch (IOException e) {
            throw new RuntimeException("读取station文件时错误");
        }
    }
}
