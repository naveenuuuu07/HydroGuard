import java.util.Scanner;

public class HydroGuard {

    // ==============================
    // SENSOR DATA
    // ==============================

    static double flowRate = 540;
    static double pressure = 4.3;
    static double production = 52;
    static double historicalUsage = 32400;

    // ==============================
    // ANALYSIS RESULTS
    // ==============================

    static double anomalyScore = 8;
    static double waterLoss = 0;
    static double dailyLoss = 0;
    static double financialLoss = 0;

    static String leakZone = "NONE";
    static String systemStatus = "NORMAL";


    // ==============================
    // MAIN PROGRAM
    // ==============================

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println();
        System.out.println("=================================================");
        System.out.println("                 HYDROGUARD");
        System.out.println("          INDUSTRIAL WATER INTELLIGENCE");
        System.out.println("=================================================");
        System.out.println();

        System.out.println("SYSTEM INITIALIZING...");
        System.out.println("Connecting to simulated sensor network...");
        System.out.println("Flow Sensor       : ONLINE");
        System.out.println("Pressure Sensor   : ONLINE");
        System.out.println("Production Data   : ONLINE");
        System.out.println("AI Engine         : ONLINE");

        System.out.println();
        System.out.println("HydroGuard monitoring system is ready.");
        System.out.println();


        while (true) {

            showMenu();

            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    normalMode();
                    break;

                case 2:
                    simulateLeak();
                    break;

                case 3:
                    customAnalysis(scanner);
                    break;

                case 4:
                    displayReport();
                    break;

                case 5:
                    isolateZone();
                    break;

                case 6:
                    emergencyResponse();
                    break;

                case 7:
                    resetSystem();
                    break;

                case 8:
                    System.out.println();
                    System.out.println("HydroGuard monitoring stopped.");
                    System.out.println("System safely shut down.");
                    scanner.close();
                    return;

                default:
                    System.out.println();
                    System.out.println("Invalid option.");
                    System.out.println("Please select a number from 1 to 8.");
            }
        }
    }


    // ==============================
    // MENU
    // ==============================

    static void showMenu() {

        System.out.println();
        System.out.println("-----------------------------------------------");
        System.out.println("              HYDROGUARD CONTROL");
        System.out.println("-----------------------------------------------");

        System.out.println("1. Start Normal Monitoring");
        System.out.println("2. Simulate Leak");
        System.out.println("3. Custom Sensor Analysis");
        System.out.println("4. Generate System Report");
        System.out.println("5. Isolate Zone C (Simulation)");
        System.out.println("6. Emergency Response (Simulation)");
        System.out.println("7. Reset System");
        System.out.println("8. Exit");

        System.out.println("-----------------------------------------------");
    }


    // ==============================
    // NORMAL MODE
    // ==============================

    static void normalMode() {

        flowRate = 540;
        pressure = 4.3;
        production = 52;
        historicalUsage = 32400;

        analyzeSystem();

        systemStatus = "NORMAL";
        leakZone = "NONE";

        System.out.println();
        System.out.println("===============================================");
        System.out.println("             NORMAL MONITORING");
        System.out.println("===============================================");

        System.out.println("Flow Rate       : " + flowRate + " L/min");
        System.out.println("Pressure        : " + pressure + " bar");
        System.out.println("Production      : " + production + "%");
        System.out.println("Daily Usage     : " + historicalUsage + " L");

        System.out.println();

        System.out.println("System Status   : " + systemStatus);
        System.out.println("Anomaly Score   : "
                + String.format("%.1f", anomalyScore) + "%");

        System.out.println();

        System.out.println("RESULT:");
        System.out.println("Water consumption matches expected");
        System.out.println("production activity.");

        System.out.println("===============================================");
    }


    // ==============================
    // LEAK SIMULATION
    // ==============================

    static void simulateLeak() {

        flowRate = 780;
        pressure = 3.2;
        production = 52;
        historicalUsage = 32400;

        analyzeSystem();

        systemStatus = "ANOMALY DETECTED";

        System.out.println();
        System.out.println("===============================================");
        System.out.println("              ⚠ LEAK SIMULATION");
        System.out.println("===============================================");

        System.out.println();

        System.out.println("SENSOR READINGS");
        System.out.println("-----------------------------------------------");

        System.out.println("Flow Rate       : " + flowRate + " L/min");
        System.out.println("Pressure        : " + pressure + " bar");
        System.out.println("Production      : " + production + "%");
        System.out.println("Historical Use  : " + historicalUsage + " L");

        System.out.println();

        System.out.println("AI ANALYSIS");
        System.out.println("-----------------------------------------------");

        System.out.println("Anomaly Score   : "
                + String.format("%.1f", anomalyScore) + "%");

        System.out.println("System Status   : " + systemStatus);

        System.out.println("Probable Zone   : " + leakZone);

        System.out.println();

        System.out.println("IMPACT ESTIMATION");
        System.out.println("-----------------------------------------------");

        System.out.println("Water Loss      : "
                + String.format("%.1f", waterLoss)
                + " L/min");

        System.out.println("Daily Loss      : "
                + String.format("%.1f", dailyLoss)
                + " L/day");

        System.out.println("Financial Impact: ₹"
                + String.format("%.2f", financialLoss)
                + " /day");

        System.out.println();

        System.out.println("RECOMMENDATION");
        System.out.println("-----------------------------------------------");

        System.out.println("Inspect " + leakZone);
        System.out.println("Check pressure drop and abnormal flow.");
        System.out.println("Consider simulated zone isolation.");

        System.out.println("===============================================");
    }


    // ==============================
    // CUSTOM ANALYSIS
    // ==============================

    static void customAnalysis(Scanner scanner) {

        System.out.println();
        System.out.println("===============================================");
        System.out.println("             CUSTOM SENSOR ANALYSIS");
        System.out.println("===============================================");

        System.out.print("Flow Rate (L/min): ");
        flowRate = scanner.nextDouble();

        System.out.print("Pressure (bar): ");
        pressure = scanner.nextDouble();

        System.out.print("Production (%): ");
        production = scanner.nextDouble();

        System.out.print("Historical Usage (L/day): ");
        historicalUsage = scanner.nextDouble();

        analyzeSystem();

        System.out.println();
        System.out.println("-----------------------------------------------");
        System.out.println("                 RESULT");
        System.out.println("-----------------------------------------------");

        System.out.println("Anomaly Score : "
                + String.format("%.1f", anomalyScore) + "%");

        if (anomalyScore >= 70) {

            systemStatus = "HIGH ANOMALY";

            System.out.println();
            System.out.println("⚠ HIGH ANOMALY DETECTED");
            System.out.println("Possible water leak.");

            System.out.println("Probable Zone: " + leakZone);

        } else if (anomalyScore >= 40) {

            systemStatus = "WARNING";

            System.out.println();
            System.out.println("⚠ WARNING");

            System.out.println("Unusual water consumption detected.");

        } else {

            systemStatus = "NORMAL";
            leakZone = "NONE";

            System.out.println();
            System.out.println("✓ SYSTEM NORMAL");

            System.out.println("No significant anomaly detected.");
        }

        System.out.println("-----------------------------------------------");
    }


    // ==============================
    // AI ANALYSIS ENGINE
    // ==============================

    static void analyzeSystem() {

        double flowScore =
                calculateFlowScore();

        double pressureScore =
                calculatePressureScore();

        double productionScore =
                calculateProductionScore();


        /*
         * HydroGuard combines multiple signals.
         *
         * Flow       = 40%
         * Pressure   = 35%
         * Production = 25%
         */

        anomalyScore =
                (flowScore * 0.40)
                +
                (pressureScore * 0.35)
                +
                (productionScore * 0.25);


        anomalyScore =
                Math.min(100,
                Math.max(0, anomalyScore));


        determineLeakZone();

        calculateWaterLoss();

        dailyLoss =
                waterLoss * 60 * 24;

        /*
         * Demonstration cost estimate.
         * ₹0.06 per litre is used only as a
         * prototype assumption.
         */

        financialLoss =
                dailyLoss * 0.06;
    }


    // ==============================
    // FLOW ANALYSIS
    // ==============================

    static double calculateFlowScore() {

        double normalFlow = 540;

        if (flowRate <= normalFlow) {

            return 5;
        }

        double increase =
                ((flowRate - normalFlow)
                / normalFlow) * 100;

        return Math.min(100, increase * 3);
    }


    // ==============================
    // PRESSURE ANALYSIS
    // ==============================

    static double calculatePressureScore() {

        double normalPressure = 4.3;

        if (pressure >= 4.0) {

            return 5;
        }

        double pressureDrop =
                ((normalPressure - pressure)
                / normalPressure) * 100;

        return Math.min(100,
                pressureDrop * 4);
    }


    // ==============================
    // PRODUCTION ANALYSIS
    // ==============================

    static double calculateProductionScore() {

        /*
         * Important idea:
         *
         * Higher production can naturally
         * cause higher water consumption.
         *
         * Therefore HydroGuard checks whether
         * the water increase makes sense compared
         * with production activity.
         */

        if (production >= 80
                && flowRate > 700) {

            return 20;
        }


        if (production < 60
                && flowRate > 650) {

            return 90;
        }


        if (production < 40
                && flowRate > 600) {

            return 95;
        }


        return 10;
    }


    // ==============================
    // LEAK LOCATION
    // ==============================

    static void determineLeakZone() {

        if (anomalyScore >= 70) {

            if (pressure < 3.5
                    && flowRate > 700) {

                leakZone =
                        "ZONE C - PRODUCTION LINE";

            } else {

                leakZone =
                        "ZONE B - PROCESSING AREA";
            }

        } else {

            leakZone = "NONE";
        }
    }


    // ==============================
    // WATER LOSS
    // ==============================

    static void calculateWaterLoss() {

        double normalFlow = 540;

        if (flowRate > normalFlow) {

            waterLoss =
                    flowRate - normalFlow;

        } else {

            waterLoss = 0;
        }
    }


    // ==============================
    // ZONE ISOLATION
    // ==============================

    static void isolateZone() {

        System.out.println();
        System.out.println("===============================================");
        System.out.println("          ZONE ISOLATION SIMULATION");
        System.out.println("===============================================");

        if (anomalyScore < 70) {

            System.out.println();
            System.out.println("No high-confidence anomaly is active.");
            System.out.println("Zone isolation simulation not required.");

            return;
        }

        System.out.println();
        System.out.println("Target Zone : " + leakZone);

        System.out.println("Simulating isolation...");

        flowRate = 560;

        calculateWaterLoss();

        dailyLoss =
                waterLoss * 60 * 24;

        financialLoss =
                dailyLoss * 0.06;

        systemStatus =
                "ZONE ISOLATED - SIMULATION";

        System.out.println();
        System.out.println("✓ Zone isolation simulated.");
        System.out.println("New Flow Rate : " + flowRate + " L/min");

        System.out.println("Remaining Estimated Loss : "
                + String.format("%.1f", waterLoss)
                + " L/min");

        System.out.println("===============================================");
    }


    // ==============================
    // EMERGENCY RESPONSE
    // ==============================

    static void emergencyResponse() {

        System.out.println();
        System.out.println("===============================================");
        System.out.println("        EMERGENCY RESPONSE SIMULATION");
        System.out.println("===============================================");

        System.out.println();

        System.out.println("[1] Generating maintenance alert...");
        System.out.println("[2] Marking affected zone...");
        System.out.println("[3] Preparing inspection request...");
        System.out.println("[4] Updating monitoring status...");

        systemStatus =
                "EMERGENCY RESPONSE ACTIVE";

        System.out.println();
        System.out.println("✓ Response workflow simulated.");
        System.out.println();
        System.out.println("Affected Zone : " + leakZone);
        System.out.println("Status        : " + systemStatus);

        System.out.println();
        System.out.println("NOTE:");
        System.out.println("This is a software demonstration.");
        System.out.println("No physical equipment is controlled.");

        System.out.println("===============================================");
    }


    // ==============================
    // REPORT
    // ==============================

    static void displayReport() {

        System.out.println();
        System.out.println("=================================================");
        System.out.println("              HYDROGUARD SYSTEM REPORT");
        System.out.println("=================================================");

        System.out.println();

        System.out.println("SYSTEM STATUS");
        System.out.println("-----------------------------------------------");

        System.out.println("Status          : " + systemStatus);

        System.out.println();

        System.out.println("SENSOR DATA");
        System.out.println("-----------------------------------------------");

        System.out.println("Flow Rate       : "
                + flowRate + " L/min");

        System.out.println("Pressure        : "
                + pressure + " bar");

        System.out.println("Production      : "
                + production + "%");

        System.out.println("Historical Use  : "
                + historicalUsage + " L/day");

        System.out.println();

        System.out.println("AI ANALYSIS");
        System.out.println("-----------------------------------------------");

        System.out.println("Anomaly Score   : "
                + String.format("%.1f", anomalyScore)
                + "%");

        System.out.println("Probable Zone   : "
                + leakZone);

        System.out.println();

        System.out.println("RESOURCE IMPACT");
        System.out.println("-----------------------------------------------");

        System.out.println("Water Loss      : "
                + String.format("%.1f", waterLoss)
                + " L/min");

        System.out.println("Daily Loss      : "
                + String.format("%.1f", dailyLoss)
                + " L/day");

        System.out.println("Financial Impact: ₹"
                + String.format("%.2f", financialLoss)
                + " /day");

        System.out.println();

        System.out.println("=================================================");
    }


    // ==============================
    // RESET
    // ==============================

    static void resetSystem() {

        flowRate = 540;
        pressure = 4.3;
        production = 52;
        historicalUsage = 32400;

        anomalyScore = 8;

        waterLoss = 0;
        dailyLoss = 0;
        financialLoss = 0;

        leakZone = "NONE";

        systemStatus = "NORMAL";

        System.out.println();
        System.out.println("===============================================");
        System.out.println("              SYSTEM RESET");
        System.out.println("===============================================");

        System.out.println();
        System.out.println("All simulated sensor values restored.");
        System.out.println("HydroGuard is ready for monitoring.");

        System.out.println("===============================================");
    }
}