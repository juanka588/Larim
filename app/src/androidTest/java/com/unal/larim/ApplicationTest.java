package com.unal.larim;

import android.app.Application;
import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends TestSuite {
    public ApplicationTest() {
        super(Application.class);
    }
    public static Test suite() {
        return new TestSuiteBuilder(ApplicationTest.class)
                .includeAllPackagesUnderHere().build();
    }
}