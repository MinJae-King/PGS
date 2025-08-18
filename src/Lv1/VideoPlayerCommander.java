package Lv1;

public class VideoPlayerCommander {
    public static void main(String[] args) {
        String video_len = "34:33";
        String pos = "13:00";
        String op_start = "00:55";
        String op_end = "02:55";
        String[] commands = {"next", "skip", "prev"};

        Solution solution = new Solution();
        String result = solution.solution(video_len, pos, op_start, op_end, commands);
        System.out.println(result);
    }
}

class Solution {
    public String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {
        int videoLen = toSeconds(video_len);
        int now = toSeconds(pos);
        int opStart = toSeconds(op_start);
        int opEnd = toSeconds(op_end);

        for (String cmd : commands) {
            if (cmd.equals("prev")) {
                now = Math.max(now - 10, 0);
            } else if (cmd.equals("next")) {
                now = Math.min(now + 10, videoLen);
            } else if (cmd.equals("skip")) {
                if (now >= opStart && now <= opEnd) {
                    now = opEnd;
                }
                continue;
            }

            if (!cmd.equals("skip") && (now >= opStart && now <= opEnd)) {
                now = opEnd;
            }
        }

        return toTime(now); // 최종 위치 반환
    }

    // 문자열 "mm:ss"를 초로 변환
    private int toSeconds(String time) {
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }
    // 초를 문자열로 변환
    private String toTime(int sec) {
        int m = sec / 60;
        int s = sec % 60;
        return String.format("%02d:%02d", m, s);
    }
}

