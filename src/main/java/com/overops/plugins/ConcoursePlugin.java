package com.overops.plugins;

import com.overops.plugins.model.QualityReport;
import com.overops.plugins.model.Config;
import com.overops.plugins.step.CreateReportStep;
import com.overops.plugins.step.RenderReportStep;
import com.overops.plugins.step.CreateConfigStep;

import java.util.Arrays;

public class ConcoursePlugin {

    public static void run(String[] args) {
        boolean status = true;
        try {
            Config config = new CreateConfigStep().run(args);
            QualityReport report = new CreateReportStep().run(config);
            new RenderReportStep().run(report);
            status = report.isStable();
        } catch (Exception e) {
            status = false;
            printError(e);
        }
        System.exit(status ? 0 : 1);
    }

    private static void printError(Exception e) {
        Context context = DependencyInjector.getImplementation(Context.class);
        context.getOutputStream().printlnError("Exceptions: " + e.toString());
        context.getOutputStream().printlnError("Trace: " + Arrays.toString(e.getStackTrace()));
    }
}
