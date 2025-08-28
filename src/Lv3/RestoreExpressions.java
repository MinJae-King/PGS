package Lv3;

public class RestoreExpressions {
    // 문제 이해 완료 / 코드 로직은 추후 예정
    public static void main(String[] args) {
        // 테스트용 입력 수식 배열
        String[] expressions = {
                "2 - 1 = 1",
                "2 + 2 = X",
                "7 + 4 = X",
                "5 - 5 = X"
        };

        // 복원 결과 실행
        String[] result = solution(expressions);
        for (String res : result) {
            System.out.println(res);
        }
    }

    public static String[] solution(String[] expressions) {
        // X 수식의 결과만 임시 저장할 배열 (최대 길이는 입력 수식 개수만큼)
        String[] temp = new String[expressions.length];
        int index = 0; // 결과에 몇 개 넣었는지 추적할 변수

        // 진법 후보 체크용 배열 (2~9진법만 사용하므로 인덱스 2~9 사용)
        boolean[] valid = new boolean[10];

        // 진법 2~9에 대해 하나씩 유효성 검사
        for (int base = 2; base <= 9; base++) {
            boolean ok = true; // 해당 진법이 유효한지 표시

            // 모든 수식을 순회
            for (String exp : expressions) {
                // 수식을 띄어쓰기 기준으로 분리
                String[] p = exp.split(" ");
                String a = p[0], op = p[1], b = p[2], c = p[4];

                // 각 숫자가 해당 진법에 유효한 숫자인지 확인
                if (!isValid(a, base) || !isValid(b, base)) {
                    ok = false;
                    break;
                }

                // a, b를 진법 기준으로 10진수로 변환
                int x = Integer.parseInt(a, base);
                int y = Integer.parseInt(b, base);

                // 연산 수행 (덧셈 or 뺄셈)
                int calc = op.equals("+") ? x + y : x - y;

                // 음수는 표현 불가능하므로 진법 후보에서 제외
                if (calc < 0) {
                    ok = false;
                    break;
                }

                // 결과가 X가 아닌 경우 → 실제 결과값도 비교해야 함
                if (!c.equals("X")) {
                    // 결과값 숫자 유효성 체크
                    if (!isValid(c, base)) {
                        ok = false;
                        break;
                    }

                    // 결과값을 진법으로 변환해서 비교
                    int z = Integer.parseInt(c, base);
                    if (z != calc) {
                        ok = false;
                        break;
                    }
                }
            }

            // 해당 진법이 모든 수식에서 유효하면 후보로 인정
            valid[base] = ok;
        }

        // 이제 X가 들어있는 수식만 따로 처리
        for (String exp : expressions) {
            String[] p = exp.split(" ");
            String a = p[0], op = p[1], b = p[2], c = p[4];

            // 결과가 X가 아니라면 건너뜀
            if (!c.equals("X")) continue;

            String result = null;      // 가능한 결과 저장
            boolean multiple = false; // 결과가 여러 개면 true

            // 유효한 진법 후보들 중에서 하나씩 계산해봄
            for (int base = 2; base <= 9; base++) {
                if (!valid[base]) continue;

                // 진법 기준 숫자 유효성 확인
                if (!isValid(a, base) || !isValid(b, base)) continue;

                // 연산 수행
                int x = Integer.parseInt(a, base);
                int y = Integer.parseInt(b, base);
                int calc = op.equals("+") ? x + y : x - y;
                if (calc < 0) continue;

                // 결과를 해당 진법 문자열로 변환
                String s = Integer.toString(calc, base);

                // 처음 나오는 결과는 저장
                if (result == null) {
                    result = s;
                } else if (!result.equals(s)) {
                    // 다른 결과가 나오면 multiple 플래그 true
                    multiple = true;
                    break;
                }
            }

            // 결과 확정
            if (result == null || multiple) {
                temp[index++] = a + " " + op + " " + b + " = ?";
            } else {
                temp[index++] = a + " " + op + " " + b + " = " + result;
            }
        }

        // 최종 결과 배열 만들기 (X 수식만 정리해서 반환)
        String[] answer = new String[index];
        for (int i = 0; i < index; i++) {
            answer[i] = temp[i];
        }

        return answer;
    }

    // 해당 문자열이 주어진 진법에서 유효한 숫자인지 확인
    private static boolean isValid(String s, int base) {
        for (int i = 0; i < s.length(); i++) {
            // 한 자리라도 base 이상이면 유효하지 않음
            if (s.charAt(i) - '0' >= base) return false;
        }
        return true;
    }
}
