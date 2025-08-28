package Lv2;

public class AnalogClockAlarm {

    public static void main(String[] args) {
        System.out.println(solution(0, 5, 30, 0, 7, 0));      // 2
        System.out.println(solution(12, 0, 0, 12, 0, 30));    // 1
        System.out.println(solution(0, 6, 1, 0, 6, 6));       // 0
        System.out.println(solution(11, 59, 30, 12, 0, 0));   // 1
        System.out.println(solution(11, 58, 59, 11, 59, 0));  // 1
        System.out.println(solution(1, 5, 5, 1, 5, 6));       // 2
        System.out.println(solution(0, 0, 0, 23, 59, 59));    // 2852
    }

    public static int solution(int h1, int m1, int s1, int h2, int m2, int s2) {
        int answer = 0;

        int start = h1 * 3600 + m1 * 60 + s1; // 시작 시간 (초 단위)
        int end = h2 * 3600 + m2 * 60 + s2;   // 끝 시간 (초 단위)

        // 시작 시각이 0시 또는 12시 정각이면 알람 한 번 울림
        if (start == 0 || start == 43200) {
            answer++;
        }

        while (start < end) {
            double hCur = (start / 120.0) % 360;        // 시침 각도
            double mCur = (start / 10.0) % 360;         // 분침 각도
            double sCur = (start * 6.0) % 360;          // 초침 각도

            double hNext = ((start + 1) / 120.0) % 360;
            double mNext = ((start + 1) / 10.0) % 360;
            double sNext = ((start + 1) * 6.0) % 360;

            // 0도면 360도로 바꿔줌 (비교 편하게 하기 위해)
            if (hNext == 0) hNext = 360;
            if (mNext == 0) mNext = 360;
            if (sNext == 0) sNext = 360;

            if (sCur < hCur && sNext >= hNext) answer++;
            if (sCur < mCur && sNext >= mNext) answer++;
            if (sNext == hNext && sNext == mNext) answer--;

            start++;
        }

        return answer;
    }
}
