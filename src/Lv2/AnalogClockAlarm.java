package Lv2;

public class AnalogClockAlarm {
    // 시간은 12*60*60 = 43200 / 360 / 43200 = 1/120
    // 분은 60*60 = 3600 / 360 / 3600 = 1/10
    // 초는 60 = 60 / 360 / 60 = 6
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

        // 시작시간과 끝시간 초로 변환
        int start = h1 * 3600 + m1 * 60 + s1;
        int end = h2 * 3600 + m2 * 60 + s2;
        
        // 시작 시각이 0시 또는 12시 정각이면 모든 침이 겹치는 순간
        if (start == 0 || start == 43200) {
            answer++;
        }

        while (start < end) {
            // 시,분,초 각도
            double hCur = (start / 120.0) % 360;
            double mCur = (start / 10.0) % 360;
            double sCur = (start * 6.0) % 360;

            // 1초뒤 시,분,초 각도
            double hNext = ((start + 1) / 120.0) % 360;
            double mNext = ((start + 1) / 10.0) % 360;
            double sNext = ((start + 1) * 6.0) % 360;

            // 0도면 360도로 바꿔줌 (비교 편하게 하기 위해)
            // 이 과정을 거쳐야 테스트케이스 통과
            if (hNext == 0) hNext = 360;
            if (mNext == 0) mNext = 360;
            if (sNext == 0) sNext = 360;
            
            // 1초 전에는 초침이 시침보다 뒤, 후에는 초침이 시침보다 앞
            if (sCur < hCur && sNext >= hNext) answer++;
            // 1초 전에는 초침이 분침보다 뒤, 후에는 초침이 분침보다 앞
            if (sCur < mCur && sNext >= mNext) answer++;
            // 세 바늘이 모두 같은 경우에는 감소. -> 중복 제거
            if (sNext == hNext && sNext == mNext) answer--;

            start++;
        }

        return answer;
    }
}
