/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-persistence - BaseUnitTest.java
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.github.cataclysmuprisingp.myapp.unitTest;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.github.cataclysmuprisingp.myapp.TestConfig;

@SpringBootTest(classes = TestConfig.class, webEnvironment = WebEnvironment.NONE)
@Rollback
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class BaseUnitTest extends AbstractTransactionalTestNGSpringContextTests {
    protected static final long TEST_CREATE_USER_ID = 10009l;
    protected static final long TEST_UPDATE_USER_ID = 90001l;
    private static final Logger testLogger = LogManager.getLogger("testLogs." + BaseUnitTest.class.getName());

    @BeforeMethod
    public void beforeMethod(Method method) {
        testLogger.info("***** Unit-TEST : Testing method '" + method.getName() + "' has started. *****");
    }

    @AfterMethod
    public void afterMethod(Method method) {
        testLogger.info("----- Unit-TEST : Testing method '" + method.getName() + "' has finished. -----");
    }

    protected <T> void showEntriesOfCollection(Collection<T> collection) {
        if (collection != null) {
            Iterator<?> iterator = collection.iterator();
            while (iterator.hasNext()) {
                Object obj = iterator.next();
                testLogger.info(" >>> " + obj.toString());
            }
        }
    }
}
