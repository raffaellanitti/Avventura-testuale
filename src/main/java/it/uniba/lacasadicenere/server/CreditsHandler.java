package it.uniba.lacasadicenere.server;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Handler per l'endpoint /api/credits
 * Restituisce una pagina HTML con i crediti del gioco.
 */
public class CreditsHandler extends HttpHandler {
    
    @Override
    public void service(Request request, Response response) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        
        if ("OPTIONS".equalsIgnoreCase(request.getMethod().getMethodString())) {
            response.setStatus(200);
            return;
        }

        if (!"GET".equalsIgnoreCase(request.getMethod().getMethodString())) {
            sendErrorResponse(response, 405, "Metodo non permesso. Usa GET.");
            return;
        }
        
        try {
            sendHtmlResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(response, 500, "Errore interno del server: " + e.getMessage());
        }
    }
    
    /**
     * Invia la pagina HTML con i crediti.
     */
    private void sendHtmlResponse(Response response) throws IOException {
        response.setStatus(200);
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String html = generateHtml();
        
        OutputStream out = response.getOutputStream();
        out.write(html.getBytes(StandardCharsets.UTF_8));
        out.flush();
    }
    
    /**
     * Genera l'HTML della pagina crediti.
     */
    private String generateHtml() {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <title>La Casa di Cenere - Crediti</title>\n");
        html.append("    <style>\n");
        html.append("        body {\n");
        html.append("            font-family: Arial, sans-serif;\n");
        html.append("            background-color: #2d2d35;\n");
        html.append("            color: #c8dcff;\n");
        html.append("            margin: 0;\n");
        html.append("            padding: 20px;\n");
        html.append("        }\n");
        html.append("        h1 {\n");
        html.append("            text-align: center;\n");
        html.append("            color: #ffffff;\n");
        html.append("        }\n");
        html.append("        h2 {\n");
        html.append("            color: #c8dcff;\n");
        html.append("            border-bottom: 2px solid #c8dcff;\n");
        html.append("            padding-bottom: 5px;\n");
        html.append("        }\n");
        html.append("        .container {\n");
        html.append("            max-width: 800px;\n");
        html.append("            margin: 0 auto;\n");
        html.append("            background-color: #1e1e23;\n");
        html.append("            padding: 30px;\n");
        html.append("            border: 2px solid #c8dcff;\n");
        html.append("        }\n");
        html.append("        p {\n");
        html.append("            line-height: 1.6;\n");
        html.append("        }\n");
        html.append("        ul {\n");
        html.append("            list-style-type: square;\n");
        html.append("        }\n");
        html.append("        li {\n");
        html.append("            margin: 5px 0;\n");
        html.append("        }\n");
        html.append("        .footer {\n");
        html.append("            text-align: center;\n");
        html.append("            margin-top: 30px;\n");
        html.append("            padding-top: 20px;\n");
        html.append("            border-top: 1px solid #c8dcff;\n");
        html.append("            font-size: 14px;\n");
        html.append("        }\n");
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <div class=\"container\">\n");
        html.append("        <h1>La Casa di Cenere</h1>\n");
        html.append("        <p style=\"text-align: center; font-style: italic;\">Avventura Testuale</p>\n");
        html.append("        \n");
        html.append("        <h2>Descrizione</h2>\n");
        html.append("        <p>\n");
        html.append("            L'avventura testuale La Casa di Cenere e' ambientata in una misteriosa villa avvolta da un'atmosfera gotica e surreale.\n");
        html.append("            Dopo essersi rifugiato al suo interno per cercare riparo da una tempesta improvvisa, il protagonista scopre ben presto\n");
        html.append("            di essere rimasto intrappolato tra le sue mura. Ogni stanza nasconde segreti ed enigmi. Per riuscire a fuggire,\n");
        html.append("            il giocatore dovra' esplorare la casa, raccogliere indizi e trovare tre oggetti necessari per completare un antico rituale.\n");
        html.append("            Solo cosi' la casa potra' finalmente riposare.\n");
        html.append("        </p>\n");
        html.append("        \n");
        html.append("        <h2>Sviluppatori</h2>\n");
        html.append("        <ul>\n");
        html.append("            <li>Raffaella Nitti</li>\n");
        html.append("            <li>Aurora Marinelli</li>\n");
        html.append("        </ul>\n");
        html.append("        \n");
        html.append("        <h2>Tecnologie</h2>\n");
        html.append("        <ul>\n");
        html.append("            <li>Linguaggio: Java 21</li>\n");
        html.append("            <li>GUI: Swing</li>\n");
        html.append("            <li>Database: H2</li>\n");
        html.append("            <li>Server REST: Grizzly</li>\n");
        html.append("            <li>Build: Maven</li>\n");
        html.append("        </ul>\n");
        html.append("        \n");
        html.append("        <div class=\"footer\">\n");
        html.append("            <p>Progetto per l'esame di Metodi Avanzati di Programmazione</p>\n");
        html.append("            <p>Universita' degli Studi di Bari Aldo Moro - 2025</p>\n");
        html.append("        </div>\n");
        html.append("    </div>\n");
        html.append("</body>\n");
        html.append("</html>");
        
        return html.toString();
    }
    
    /**
     * Invia una risposta di errore in formato HTML.
     */
    private void sendErrorResponse(Response response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        StringBuilder errorHtml = new StringBuilder();
        errorHtml.append("<!DOCTYPE html>\n");
        errorHtml.append("<html>\n");
        errorHtml.append("<head>\n");
        errorHtml.append("    <meta charset=\"UTF-8\">\n");
        errorHtml.append("    <title>Errore ").append(statusCode).append("</title>\n");
        errorHtml.append("</head>\n");
        errorHtml.append("<body style=\"background-color: #2d2d35; color: #c8dcff; text-align: center; padding: 50px;\">\n");
        errorHtml.append("    <h1 style=\"color: #ff6b6b;\">Errore ").append(statusCode).append("</h1>\n");
        errorHtml.append("    <p>").append(message).append("</p>\n");
        errorHtml.append("</body>\n");
        errorHtml.append("</html>");
        
        OutputStream out = response.getOutputStream();
        out.write(errorHtml.toString().getBytes(StandardCharsets.UTF_8));
        out.flush();
    }
}