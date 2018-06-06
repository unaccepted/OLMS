
package com.nrifintech.olmsb;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * {@code IrisAbstractTestCase}. Base JUnit test case abstraction for iris 
 * reconciliation project.  
 * 
 * @author Amols
 * @version $Revision: 1.1 $, $Date: 2016/09/01 11:34:27 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context-junit.xml"})
@TransactionConfiguration(defaultRollback = true)
public abstract class AbstractTestCase {
	
	@Before
	public void init() {
	}

	@AfterClass
	public static void DestroyApplication() {
	}
}