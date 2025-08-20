package Lv1;

import java.io.*;

public class BandageHealing {

    public static void main(String[] args) throws IOException {

        BandageHealing bandageHealing = new BandageHealing();

        int[] bandage = {5, 1, 5};
        int health = 30;
        int[][] attack = {{2, 10}, {9,15}, {10, 5}, {11, 5}};

        int result = bandageHealing.solution(bandage, health, attack);
        System.out.println("최종 체력 : " + result);
    }

    public int solution(int[] bandage, int health, int[][] attacks) {

        int t = bandage[0]; // 시전 시간
        int x = bandage[1]; // 초당 회복량
        int y = bandage[2]; // 추가 회복량

        int currentHealth = health; // 현재 체력
        int healHealth = health; // 회복 후 체력
        
        int successTime = 0;
        int attackIndex = 0; // 해당 인덱스를 통해서 공격 순서를 판단
        
        // 1초부터 마지막 공격까지 순차적으로 진행
        for (int i = 1; i <= attacks[attacks.length - 1][0]; i++) {
            // 지금 시간이 공격받는 시점이면 && index가 범위내부이면
            if (attackIndex < attacks.length && attacks[attackIndex][0] == i) {
                // 체력 감소
                currentHealth = currentHealth - attacks[attackIndex][1];

                if (currentHealth < 0) { // 딸피일 때 사망
                    return -1;
                }
                // 연속 시간 초기화 후 다음 공격 진행을 위해 인덱스 증가
                successTime = 0;
                attackIndex++;
            } else {
                // 회복 가능한 시간인 경우 초당 회복량 증가, successTime 1증가
                currentHealth = Math.min(currentHealth + x, healHealth);
                successTime++;

                // 연송 성공 이면 추가 회복
                if (successTime == t) {
                    currentHealth = Math.min(currentHealth + y, healHealth);
                    successTime = 0;
                }
            }
        }
        return currentHealth;
    }
}
