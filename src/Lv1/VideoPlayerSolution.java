package Lv1;

public class VideoPlayerSolution {

    public static void main(String[] args) {
        VideoPlayerSolution sol = new VideoPlayerSolution();

        String result1 = sol.solution("10:55", "06:55", "00:05", "00:15", new String[]{"prev", "next", "next"});
        System.out.println("테스트1 결과: " + result1); // 예상: 06:55

        String result2 = sol.solution("07:22", "04:07", "04:05", "04:15", new String[]{"next"});
        System.out.println("테스트2 결과: " + result2); // 예상: 04:15

        String result3 = sol.solution("34:33", "02:55", "13:00", "00:55", new String[]{"next", "prev"});
        System.out.println("테스트3 결과: " + result3); // 예상: 13:00
    }

    public String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {
        int videoLen = toSec(video_len);
        int now = toSec(pos);
        int opStart = toSec(op_start);
        int opEnd = toSec(op_end);

        boolean isWrapped = opStart > opEnd;
        if (isWrapped) opEnd += 86400;

        int tempNow = now;
        if (isWrapped && tempNow < opStart) tempNow += 86400;
        if (tempNow >= opStart && tempNow <= opEnd) {
            now = Math.min(opEnd % 86400, videoLen); // 초기 오프닝 점프
        }

        for (String cmd : commands) {
            if (cmd.equals("prev")) {
                now = Math.max(0, now - 10);
            } else {
                now = Math.min(now + 10, videoLen);
            }

            tempNow = now;
            if (isWrapped && tempNow < opStart) tempNow += 86400;
            if (tempNow >= opStart && tempNow <= opEnd) {
                now = Math.min(opEnd % 86400, videoLen); // 반복 중 오프닝 점프
            }
        }

        return String.format("%02d:%02d", now / 60, now % 60);
    }

    private int toSec(String time) {
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }
}
