import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class HydroGuardServer {

    static double flow = 540;
    static double pressure = 4.3;
    static double production = 52;
    static double water = 32400;

    static boolean leakDetected = false;
    static boolean zoneIsolated = false;
    static boolean emergency = false;

    public static void main(String[] args) throws Exception {

        // =====================================================
        // RENDER DEPLOYMENT CONFIGURATION
        // =====================================================

        // Render provides the PORT environment variable.
        // If running locally, it will use port 8080.
        int port = Integer.parseInt(
                System.getenv().getOrDefault("PORT", "8080")
        );

        // Listen on all network interfaces so Render can access
        // the Java server.
        HttpServer server = HttpServer.create(
                new InetSocketAddress("0.0.0.0", port),
                0
        );

        // =====================================================
        // WEBSITE
        // =====================================================

        server.createContext("/", HydroGuardServer::serveWebsite);

        // =====================================================
        // API ENDPOINTS
        // =====================================================

        server.createContext("/api/status", HydroGuardServer::status);
        server.createContext("/api/leak", HydroGuardServer::simulateLeak);
        server.createContext("/api/normal", HydroGuardServer::normalMode);
        server.createContext("/api/isolate", HydroGuardServer::isolateZone);
        server.createContext("/api/emergency", HydroGuardServer::emergencyResponse);
        server.createContext("/api/reset", HydroGuardServer::resetSystem);

        server.setExecutor(null);

        // =====================================================
        // SERVER INFORMATION
        // =====================================================

        System.out.println();
        System.out.println("================================================");
        System.out.println("              HYDROGUARD SERVER");
        System.out.println("       INDUSTRIAL WATER INTELLIGENCE");
        System.out.println("================================================");
        System.out.println();
        System.out.println("Java backend started successfully.");
        System.out.println();
        System.out.println("Server listening on port: " + port);
        System.out.println();
        System.out.println("Waiting for website requests...");
        System.out.println("================================================");

        server.start();
    }

    // =====================================================
    // WEBSITE FILE SERVER
    // =====================================================

    static void serveWebsite(HttpExchange exchange) throws IOException {

        String path = exchange.getRequestURI().getPath();

        if (path.equals("/")) {
            path = "/index.html";
        }

        File file = new File("." + path);

        if (!file.exists() || file.isDirectory()) {

            String response = "404 - File Not Found";

            exchange.getResponseHeaders()
                    .set("Content-Type", "text/plain; charset=UTF-8");

            exchange.sendResponseHeaders(
                    404,
                    response.getBytes(StandardCharsets.UTF_8).length
            );

            OutputStream output = exchange.getResponseBody();

            output.write(
                    response.getBytes(StandardCharsets.UTF_8)
            );

            output.close();

            return;
        }

        String contentType = getContentType(path);

        exchange.getResponseHeaders()
                .set("Content-Type", contentType);

        byte[] data = readFile(file);

        exchange.sendResponseHeaders(
                200,
                data.length
        );

        OutputStream output = exchange.getResponseBody();

        output.write(data);

        output.close();
    }

    // =====================================================
    // READ WEBSITE FILE
    // =====================================================

    static byte[] readFile(File file) throws IOException {

        FileInputStream input = new FileInputStream(file);

        byte[] data = input.readAllBytes();

        input.close();

        return data;
    }

    // =====================================================
    // CONTENT TYPE
    // =====================================================

    static String getContentType(String path) {

        if (path.endsWith(".html")) {
            return "text/html; charset=UTF-8";
        }

        if (path.endsWith(".css")) {
            return "text/css; charset=UTF-8";
        }

        if (path.endsWith(".js")) {
            return "application/javascript; charset=UTF-8";
        }

        if (path.endsWith(".json")) {
            return "application/json; charset=UTF-8";
        }

        if (path.endsWith(".png")) {
            return "image/png";
        }

        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
            return "image/jpeg";
        }

        if (path.endsWith(".svg")) {
            return "image/svg+xml";
        }

        if (path.endsWith(".ico")) {
            return "image/x-icon";
        }

        return "text/plain; charset=UTF-8";
    }

    // =====================================================
    // STATUS API
    // =====================================================

    static void status(HttpExchange exchange) throws IOException {

        String json = createJSON(
                "SYSTEM STATUS",
                flow,
                pressure,
                production,
                water,
                calculateConfidence()
        );

        sendJSON(exchange, json);
    }

    // =====================================================
    // NORMAL MODE
    // =====================================================

    static void normalMode(HttpExchange exchange) throws IOException {

        flow = 540;
        pressure = 4.3;
        production = 52;
        water = 32400;

        leakDetected = false;
        zoneIsolated = false;
        emergency = false;

        String json = createJSON(
                "NORMAL",
                flow,
                pressure,
                production,
                water,
                8
        );

        System.out.println("Normal monitoring activated.");

        sendJSON(exchange, json);
    }

    // =====================================================
    // LEAK SIMULATION
    // =====================================================

    static void simulateLeak(HttpExchange exchange) throws IOException {

        flow = 780;
        pressure = 3.2;
        production = 52;
        water = 38420;

        leakDetected = true;
        zoneIsolated = false;
        emergency = false;

        double confidence = calculateConfidence();

        String json =
                "{"
                        + "\"status\":\"ANOMALY\","
                        + "\"flow\":" + flow + ","
                        + "\"pressure\":" + pressure + ","
                        + "\"production\":" + production + ","
                        + "\"water\":" + water + ","
                        + "\"confidence\":" + confidence + ","
                        + "\"zone\":\"ZONE C\","
                        + "\"lossPerMinute\":240,"
                        + "\"dailyLoss\":34560,"
                        + "\"financialLoss\":2074,"
                        + "\"message\":\"Abnormal water consumption detected\""
                        + "}";

        System.out.println();
        System.out.println("⚠ LEAK SIMULATION");
        System.out.println("Flow      : " + flow + " L/min");
        System.out.println("Pressure  : " + pressure + " bar");
        System.out.println("Production: " + production + "%");
        System.out.println("Zone      : ZONE C");
        System.out.println("Confidence: " + confidence + "%");

        sendJSON(exchange, json);
    }

    // =====================================================
    // ZONE ISOLATION
    // =====================================================

    static void isolateZone(HttpExchange exchange) throws IOException {

        if (!leakDetected) {

            sendJSON(
                    exchange,
                    "{\"status\":\"INFO\",\"message\":\"No active anomaly to isolate.\"}"
            );

            return;
        }

        zoneIsolated = true;

        // Simulation values after isolation
        flow = 610;
        water = 35000;

        String json =
                "{"
                        + "\"status\":\"ISOLATED\","
                        + "\"zone\":\"ZONE C\","
                        + "\"flow\":" + flow + ","
                        + "\"water\":" + water + ","
                        + "\"lossPerMinute\":35,"
                        + "\"dailyLoss\":5040,"
                        + "\"financialLoss\":302,"
                        + "\"message\":\"Zone C isolation simulated successfully\""
                        + "}";

        System.out.println("Zone C isolation simulated.");

        sendJSON(exchange, json);
    }

    // =====================================================
    // EMERGENCY RESPONSE
    // =====================================================

    static void emergencyResponse(HttpExchange exchange) throws IOException {

        emergency = true;

        String json =
                "{"
                        + "\"status\":\"EMERGENCY\","
                        + "\"message\":\"Emergency response simulation activated\","
                        + "\"actions\":["
                        + "\"Alert maintenance team\","
                        + "\"Review Zone C\","
                        + "\"Simulate containment\","
                        + "\"Generate incident record\""
                        + "]"
                        + "}";

        System.out.println("Emergency response simulation activated.");

        sendJSON(exchange, json);
    }

    // =====================================================
    // RESET SYSTEM
    // =====================================================

    static void resetSystem(HttpExchange exchange) throws IOException {

        flow = 540;
        pressure = 4.3;
        production = 52;
        water = 32400;

        leakDetected = false;
        zoneIsolated = false;
        emergency = false;

        String json = createJSON(
                "RESET",
                flow,
                pressure,
                production,
                water,
                8
        );

        System.out.println("System reset.");

        sendJSON(exchange, json);
    }

    // =====================================================
    // CALCULATE ANOMALY CONFIDENCE
    // =====================================================

    static double calculateConfidence() {

        // Prototype scoring model:
        // Flow anomaly       = 40%
        // Pressure anomaly   = 35%
        // Production context = 25%

        double flowScore = 92;
        double pressureScore = 81;
        double productionScore = 88;

        double weightedScore =
                (flowScore * 0.40)
                + (pressureScore * 0.35)
                + (productionScore * 0.25);

        return Math.round(weightedScore);
    }

    // =====================================================
    // JSON CREATOR
    // =====================================================

    static String createJSON(
            String status,
            double flow,
            double pressure,
            double production,
            double water,
            double confidence
    ) {

        return "{"
                + "\"status\":\"" + status + "\","
                + "\"flow\":" + flow + ","
                + "\"pressure\":" + pressure + ","
                + "\"production\":" + production + ","
                + "\"water\":" + water + ","
                + "\"confidence\":" + confidence
                + "}";
    }

    // =====================================================
    // SEND JSON RESPONSE
    // =====================================================

    static void sendJSON(
            HttpExchange exchange,
            String json
    ) throws IOException {

        exchange.getResponseHeaders()
                .set(
                        "Content-Type",
                        "application/json; charset=UTF-8"
                );

        exchange.getResponseHeaders()
                .set(
                        "Access-Control-Allow-Origin",
                        "*"
                );

        byte[] data =
                json.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(
                200,
                data.length
        );

        OutputStream output =
                exchange.getResponseBody();

        output.write(data);

        output.close();
    }
}