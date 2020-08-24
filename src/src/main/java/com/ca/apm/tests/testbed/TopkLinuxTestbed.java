package com.ca.apm.tests.testbed;

import com.ca.tas.resolver.ITasResolver;
import com.ca.tas.role.webapp.JavaRole;
import com.ca.tas.testbed.ITestbed;
import com.ca.tas.testbed.ITestbedFactory;
import com.ca.tas.testbed.TestBedUtils;
import com.ca.tas.testbed.Testbed;
import com.ca.tas.testbed.TestbedMachine;
import com.ca.tas.tests.annotations.TestBedDefinition;

@TestBedDefinition
public class TopkLinuxTestbed implements ITestbedFactory {
	@Override
    public ITestbed create(ITasResolver tasResolver) {
        //create the EM role
    	JavaRole java_role = new JavaRole.Builder("java_role", tasResolver).build();
        //create a machine for EM using the co7 template id label
        TestbedMachine emMachine = 
            TestBedUtils.createLinuxMachine("Linuxmachine", "co7", java_role);
        //create the test-bed
        return Testbed.create(this, emMachine);
    }

}
