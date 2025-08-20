package Lv2;

public class PuzzleGame {
    public static void main(String[] args) {
        // 문제 재 요약
        // 퍼즐 마다 난이도와 풀이시간 제시
        // 숙련도에 따라, diff <= level 풀리는 시간 times배열 안 값
        // diff > level = diff-level 번 틀리고
        // 틀릴 때 마다 time_cur + time_prev
        // 전체 시간 안에 모든 퍼즐이 풀리도록 해야 한다.

        int[] diffs = {1, 4, 4, 2};
        int[] times = {6, 3, 8, 2};
        long limit = 59;

        int result = solution(diffs, times, limit);
        System.out.println("최소 숙련도: " + result); // 예상: 3
    }

    // 이분 탐색
    public static int solution(int[] diffs, int[] times, long limit) {
        // 숙련도의 최소, 최대값 지정
        int left = 1;
        int right = 100000;
        // 가능한 숙련도 중 가장 작은 값을 저장하는 변수 선언
        int answer = right;

        // left > right 될때까지 반복
        while (left <= right) {
            // 처음 큰 숫자로는 모든 퍼즐 시간내 풀이 가능
            int mid = (left + right) / 2;

            if (canSolveAll(diffs, times, limit, mid)) {
                // true가 반환될 때마다, right값을 줄여나간다.
                answer = mid;
                right = mid - 1;
            } else { // 만약 false이면 left값을 증가시켜 level을 더 높은값으로 수행
                left = mid + 1;
            }
        }

        // 해당 while문이 모두 수행된 후, 최소 level 갱신
        return answer;
    }

    // 풀 수 있는 경우에 따라 값을 반환하는 solution 함수
    private static boolean canSolveAll(int[] diffs, int[] times, long limit, int level) {
        long totalTime = 0;

        for (int i = 0; i < diffs.length; i++) {
            int diff = diffs[i];
            int time_cur = times[i];
            // 이전 값이 0인경우 첫번째 diff인 경우에는 이전 걸리는 시간이 0
            long time_prev = (i == 0) ? 0 : times[i - 1];

            if (diff <= level) { // 한번에 풀리는 경우
                totalTime += time_cur;
            } else { // 숙련도가 부족한 경우
                int retry = diff - level;
                totalTime += retry * (time_cur + time_prev) + time_cur;
            }

            // 해당 함수 진행 중, 제한 시간 초과 시 false값 반환
            if (totalTime > limit) return false;
        }

        // 모든 퍼즐 풀이 가능 시 true
        return true;
    }
}
