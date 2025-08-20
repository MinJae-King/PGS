package Lv1;

public class VideoPlayerSolution {

    public static void main(String[] args) {
        // 예시 입력값
        String video_len = "01:00"; // 1분
        String pos = "00:00";       // 시작 위치
        String op_start = "00:10";  // 오프닝 시작
        String op_end = "00:20";    // 오프닝 끝
        String[] commands = {"next", "next", "next", "prev", "next"};

        // 실행
        String result = solution(video_len, pos, op_start, op_end, commands);
        System.out.println("최종 위치: " + result);
    }

    public static String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {
        int videoLen = toSec(video_len);
        int now = toSec(pos);
        int opStart = toSec(op_start);
        int opEnd = toSec(op_end);

        // 최초 위치도 오프닝 범위라면 바로 건너뛰기
        if (now >= opStart && now <= opEnd) {
            now = opEnd;
        }

        for (String cmd : commands) {
            if (cmd.equals("prev")) {
                now = Math.max(now - 10, 0);
            } else { // cmd.equals("next")
                now = Math.min(now + 10, videoLen);
            }

            // 이동 후에도 오프닝 범위라면 건너뛰기
            if (now >= opStart && now <= opEnd) {
                now = opEnd;
            }
        }

        return String.format("%02d:%02d", now / 60, now % 60);
    }

    private static int toSec(String time) {
        String[] parts = time.split(":");
        int mm = Integer.parseInt(parts[0]);
        int ss = Integer.parseInt(parts[1]);
        return mm * 60 + ss;
    }
}