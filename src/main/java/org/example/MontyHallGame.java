package org.example;

import java.util.*;

public class MontyHallGame {

    private static final int TOTAL_TRIALS = 1000;

    public static void main(String[] args) {
        Map<Integer, Boolean> results = new HashMap<>();
        Map<Integer, List<Integer>> moves = new HashMap<>();

        for (int i = 1; i <= TOTAL_TRIALS; i++) {
            boolean win = playMontyHallGame(moves, i);
            results.put(i, win);
        }

        // Вывод ходов игры
        for (int i = 1; i <= TOTAL_TRIALS; i++) {
            List<Integer> gameMoves = moves.get(i);

            // Проверка, чтобы избежать ошибок, если что-то пошло не так
            if (gameMoves != null && gameMoves.size() >= 3) {
                int playerChoice = gameMoves.get(0);
                int goatDoor = gameMoves.get(1);
                int switchChoice = gameMoves.get(2);

                // Получение результата для текущей игры
                boolean win = results.get(i);

                // Пояснение в консоль
                String explanation = " Игрок выбрал дверь " + playerChoice + ", " +
                        " Ведущий открывает дверь " + goatDoor + ",";

                // Проверка, изменил ли игрок свой выбор
                if (switchChoice > 0) {
                    // Если игрок уже выбрал дверь, выводим сообщение оставшейся двери
                    if (switchChoice == playerChoice) {
                        explanation += " Игрок решает оставить дверь ";
                    } else {
                        // Если игрок выбрал другую дверь, добавляем информацию об изменении выбора
                        explanation += " Игрок выбирает дверь " + switchChoice;
                    }
                } else {
                    // Если игрок не изменил свой выбор, добавляем информацию об этом
                    explanation += " Игрок решает оставить дверь ";
                }

                // Вывод строки с пояснением в консоль
                System.out.println("Игра " + i + ": " + explanation);

                // Добавление информации о выигрыше, если игрок выиграл
                if (win) {
                    System.out.println("Игрок выиграл))");
                } else {
                    System.out.println("Игрок проиграл((.");
                }
            } else {
                // Если что-то пошло не так, выведем сообщение об ошибке
                System.out.println("Ошибка при обработке хода игры " + i);
            }
        }
        // Вывод статистики
        int positiveResults = (int) results.values().stream().filter(Boolean::valueOf).count();
        int negativeResults = TOTAL_TRIALS - positiveResults;
        double positivePercentage = (double) positiveResults / TOTAL_TRIALS * 100;

        System.out.println("\nСтатистика:");
        System.out.println("Позитивные результаты: " + positiveResults);
        System.out.println("Негативные результаты: " + negativeResults);
        System.out.println("Процент позитивных результатов: " + positivePercentage + "%");
    }


    private static boolean playMontyHallGame(Map<Integer, List<Integer>> moves, int gameNumber) {
        Random random = new Random();

        int carDoor = random.nextInt(3) + 1;
        int playerChoice = random.nextInt(3) + 1;

        int goatDoor;
        do {
            goatDoor = random.nextInt(3) + 1;
        } while (goatDoor == carDoor || goatDoor == playerChoice);

        boolean switchChoice = random.nextBoolean();

        if (switchChoice) {
            do {
                playerChoice = random.nextInt(3) + 1;
            } while (playerChoice == goatDoor);
        }

        // Сохранение хода игры
        List<Integer> gameMoves = new ArrayList<>();
        gameMoves.add(playerChoice);
        gameMoves.add(goatDoor);
        gameMoves.add(
                switchChoice ? playerChoice : 0); // Если произошло изменение выбора, добавляем новый выбор
        moves.put(gameNumber, gameMoves);

        return playerChoice == carDoor;
    }
}