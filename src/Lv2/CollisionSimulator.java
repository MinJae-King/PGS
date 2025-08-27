package Lv2;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class CollisionSimulator {
    // 1. 각 포인트 번호에 대응하는 좌표 저장
    // 2. 각 로봇은 경로를 순서대로 따라가며 좌표 시뮬레이션
    //      - 이동은 r에서 c로 최단거리만 이동
    //      - 1초마다 1칸씩 겹치면 r부터
    // 3. 모든 로봇이 0초에 동시 출발
    // 각 시간마다 좌표에 도착하는 로봇 저장
    // 4. 시간별로 순회, 특정 시간 밸류값이 2 이상 -> 충돌위험 발생
    // 5. 전체 시간 동안 발생한 충돌위험의 합 출력

    public static void main(String[] args) {

        int[][] points = {
                {2, 2}, {2, 3}, {2, 7}, {6, 6}, {5, 2}
        };

        int[][] routes = {
                {2, 3, 4, 5}, {1, 3, 4, 5}
        };

        int dangerCount = solution(points, routes);
        System.out.println(dangerCount + "\n");
    }

    public static int solution(int[][] points, int[][] routes) {

        // 포인트 좌표 매핑
        Map<Integer, int[]> pointMap = new HashMap<>();
        for (int i = 0; i < points.length; i++) {
            pointMap.put(i + 1, points[i]);
        }

        // 시간별 위치 기록용 Map
        // 초마다 어떤 좌표에 몇대의 로봇이 있었는지 기록
        Map<Integer, Map<String, Integer>> timeMap = new HashMap<>();

        // 각 로봇은 경로따라 이동
        for (int[] route : routes) {
            // 시작 위치 설정 (pointMap에서 가져온 route의 좌표를 r, c 사용
            int time = 0;
            int[] start = pointMap.get(route[0]);
            int r = start[0];
            int c = start[1];

            // 0초 일 때 위치 기록
            String key = r + "," + c;
            // computeIfAbsent 기능 : time에 해당하는 Map이 없으면 새로 생성
            // 이미 그 좌표에 로봇이 있으면 +1, 없으면 -1
            // ex) 0초에 1,4 좌표에 로봇 1대 있음
            timeMap.computeIfAbsent(time, t -> new HashMap<>()).merge(key, 1, Integer::sum);

            // 디음 목적지 포인트의 좌표
            for (int i  = 1; i < route.length; i++) {
                int[] target = pointMap.get(route[i]);
                int targetR = target[0];
                int targetC = target[1];

                // r좌표 선행 이동
                while (r != targetR) {
                    if (r < targetR) {
                        r++; // 아래로 이동
                    } else {
                        r--; // 위로 이동
                    }
                    time++;
                    String pos = r + "," + c;
                    timeMap.computeIfAbsent(time, t -> new HashMap<>()).merge(pos, 1, Integer::sum);
                }

                // c 좌표 이동
                while (c != targetC) {
                    if (c < targetC) {
                        c++; // 오른쪽으로 이동
                    } else {
                        c--; // 좌로 이동
                    }
                    time++;
                    String pos = r + "," + c;
                    timeMap.computeIfAbsent(time, t -> new HashMap<>()).merge(pos, 1, Integer::sum);
                }
            }
        }

        // 위험상황 갯수 count
        int dangerCount = 0;
        for (Map<String, Integer> locationMap : timeMap.values()) {
            for (int count : locationMap.values()) {
                if (count >= 2) {
                    dangerCount++;
                }
            }
        }

        return dangerCount;
    }
}
