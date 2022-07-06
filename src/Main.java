import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static String currencySign = " ₽";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String menuOption = "";
        ArrayList<MonthlyReport> monthlyReports = new ArrayList<>();
        YearlyReport yearlyReport = null;
        while (!menuOption.equals("Выход")) {
            printMenu();
            menuOption = scanner.nextLine();
            //обработка введенных данных
            switch (menuOption) {
                case "1":
                    //Считать все месячные отчеты
                    readMonthlyReports(monthlyReports);
                    break;
                case "2":
                    //Считать годовой отчет
                    yearlyReport = new YearlyReport(2021, readFileContentsOrNull("resources/y.2021.csv"));
                    break;
                case "3":
                    //Сверить отчеты
                    if (yearlyReport != null && !monthlyReports.isEmpty()) {
                        ArrayList<Integer> problemMonths = compareReportsData(monthlyReports, yearlyReport);
                        if (!problemMonths.isEmpty()) {
                            System.out.println("Не сходятся данные за следующие месяцы:");
                            for (Integer index : problemMonths) {
                                System.out.println("\t" + convertNumberToMonth(index));
                            }
                        } else System.out.println("Команда выполнена. Данные верны!");
                    } else System.out.println("Отчеты не внесены");
                    break;
                case "4":
                    //Вывести информацию о всех месячных отчётах
                    if (!monthlyReports.isEmpty()) {
                        printMonthlyReports(monthlyReports);
                    } else System.out.println("Ежемесячные отчеты не внесены!");
                    break;
                case "5":
                    //Вывести информацию о годовом отчёте
                    if (yearlyReport != null) {
                        printYearlyReport(yearlyReport);
                    } else System.out.println("Данные годового отчета не внесены!");
                    break;
                case "Выход":
                    //Выход из программы
                    break;
                default:
                    System.out.println("Данной команды не существует. Введите корректную команду.");
                    break;
            }
        }
    }

    private static void printMonthlyReports(ArrayList<MonthlyReport> monthlyReports) {
        for (MonthlyReport monthlyReport : monthlyReports) {
            System.out.println("Отчет за " + convertNumberToMonth(monthlyReport.month) + ":");
            MonthItemData item = monthlyReport.findMostProfitableItem();
            System.out.println("\tСамый прибыльный товар:\t" + item.itemName + ", доход составил:\t" + item.getAmount() + currencySign);
            item = monthlyReport.findMostExpensiveItem();
            System.out.println("\tСамая большая трата:\t" + item.itemName + ", трата составила:\t" + item.getAmount() + currencySign);
        }
    }

    private static void printYearlyReport(YearlyReport yearlyReport) {
        System.out.println("Отчет за " + yearlyReport.year + " год");
        System.out.println("Ежемесячная прибыль:");
        HashMap<Integer,Integer> profit = yearlyReport.getProfitByMonths();
        for (Integer month : profit.keySet()){
            System.out.println("\t- " + convertNumberToMonth(month) + ": " + profit.get(month) + currencySign);
        }
        System.out.println("Средний расход за все месяцы в " + yearlyReport.year + " году составил:\t" + yearlyReport.getAverageExpense() + currencySign);
        System.out.println("Средний доход за все месяцы в " + yearlyReport.year + " году составил:\t" + yearlyReport.getAverageIncome() + currencySign);
    }

    private static ArrayList<Integer> compareReportsData(ArrayList<MonthlyReport> monthlyReports, YearlyReport yearlyReport) {
        ArrayList<Integer> problemMonths = new ArrayList<>();
        for (MonthlyReport monthlyReport : monthlyReports){
            int month = monthlyReport.month;
            if (monthlyReport.getSummaryIncome() != yearlyReport.getIncomeForMonth(month) || monthlyReport.getSummaryExpense() != yearlyReport.getExpenseForMonth(month)) problemMonths.add(month);
        }
        return problemMonths;
    }

    private static void readMonthlyReports(ArrayList<MonthlyReport> reports) {
        reports.clear();
        for (int i = 1; i <= 3; i++) {
            reports.add(new MonthlyReport(i, readFileContentsOrNull("resources/m.20210" + i + ".csv")));
        }
    }

    public static void printMenu(){ // вывод меню
        System.out.println("Что вы хотите сделать?");
        System.out.println("\t1 - Считать все месячные отчеты");
        System.out.println("\t2 - Считать годовой отчет");
        System.out.println("\t3 - Сверить отчеты");
        System.out.println("\t4 - Вывести информацию о всех месячных отчётах");
        System.out.println("\t5 - Вывести информацию о годовом отчёте");
        System.out.println("\tВыход - Выйти из программы");
    }

    private static String readFileContentsOrNull(String path)
    {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

    private static String convertNumberToMonth(int number){
        String[] months = new String[] {
                "январь",
                "февраль",
                "март",
                "апрель",
                "май",
                "июнь",
                "июль",
                "август",
                "сентябрь",
                "октябрь",
                "ноябрь",
                "декабрь"};
        if (number > 0 && number < 13){
            return months[number - 1];
        }
        else return "Ошибка получения названия месяца";
    }
}

