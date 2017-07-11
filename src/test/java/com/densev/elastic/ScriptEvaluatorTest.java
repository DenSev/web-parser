package com.densev.elastic;

import com.densev.elastic.script.ScriptEvaluator;
import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

/**
 * Created by Dzianis_Sevastseyenk on 07/11/2017.
 */
public class ScriptEvaluatorTest {

    private static final Logger LOG = LoggerFactory.getLogger(ScriptEvaluatorTest.class);

    ScriptEvaluator evaluator;

    @BeforeTest
    public void init() throws Exception {
        evaluator = new ScriptEvaluator();
        evaluator.init();
        //warm up
        evaluator.evalRaw("println 'Warming up the engine'");
    }

    @Test(invocationCount = 10)
    public void testLoadScripts() throws ScriptException {
        ScriptContext context = new SimpleScriptContext();
        Stopwatch stopwatch = Stopwatch.createStarted();
        evaluator.eval("goo.script", context);
        stopwatch.stop();
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS));
    }

    @Test(invocationCount = 10)
    public void testEvalRaw() throws ScriptException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        evaluator.evalRaw("int k = 10; for( int i = 0; i  < 10; i++){ k = k * (i + 1); }; return k;");
        stopwatch.stop();
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS));
    }
}
