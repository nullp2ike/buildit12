// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package cs.ut.domain;

import cs.ut.domain.SiteEngineerDataOnDemand;
import cs.ut.domain.SiteEngineerIntegrationTest;
import cs.ut.repository.SiteEngineerRepository;
import java.util.Iterator;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect SiteEngineerIntegrationTest_Roo_IntegrationTest {
    
    declare @type: SiteEngineerIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: SiteEngineerIntegrationTest: @ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml");
    
    declare @type: SiteEngineerIntegrationTest: @Transactional;
    
    @Autowired
    SiteEngineerDataOnDemand SiteEngineerIntegrationTest.dod;
    
    @Autowired
    SiteEngineerRepository SiteEngineerIntegrationTest.siteEngineerRepository;
    
    @Test
    public void SiteEngineerIntegrationTest.testCount() {
        Assert.assertNotNull("Data on demand for 'SiteEngineer' failed to initialize correctly", dod.getRandomSiteEngineer());
        long count = siteEngineerRepository.count();
        Assert.assertTrue("Counter for 'SiteEngineer' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void SiteEngineerIntegrationTest.testFind() {
        SiteEngineer obj = dod.getRandomSiteEngineer();
        Assert.assertNotNull("Data on demand for 'SiteEngineer' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SiteEngineer' failed to provide an identifier", id);
        obj = siteEngineerRepository.findOne(id);
        Assert.assertNotNull("Find method for 'SiteEngineer' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'SiteEngineer' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void SiteEngineerIntegrationTest.testFindAll() {
        Assert.assertNotNull("Data on demand for 'SiteEngineer' failed to initialize correctly", dod.getRandomSiteEngineer());
        long count = siteEngineerRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'SiteEngineer', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<SiteEngineer> result = siteEngineerRepository.findAll();
        Assert.assertNotNull("Find all method for 'SiteEngineer' illegally returned null", result);
        Assert.assertTrue("Find all method for 'SiteEngineer' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void SiteEngineerIntegrationTest.testFindEntries() {
        Assert.assertNotNull("Data on demand for 'SiteEngineer' failed to initialize correctly", dod.getRandomSiteEngineer());
        long count = siteEngineerRepository.count();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<SiteEngineer> result = siteEngineerRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
        Assert.assertNotNull("Find entries method for 'SiteEngineer' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'SiteEngineer' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void SiteEngineerIntegrationTest.testFlush() {
        SiteEngineer obj = dod.getRandomSiteEngineer();
        Assert.assertNotNull("Data on demand for 'SiteEngineer' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SiteEngineer' failed to provide an identifier", id);
        obj = siteEngineerRepository.findOne(id);
        Assert.assertNotNull("Find method for 'SiteEngineer' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifySiteEngineer(obj);
        Integer currentVersion = obj.getVersion();
        siteEngineerRepository.flush();
        Assert.assertTrue("Version for 'SiteEngineer' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void SiteEngineerIntegrationTest.testSaveUpdate() {
        SiteEngineer obj = dod.getRandomSiteEngineer();
        Assert.assertNotNull("Data on demand for 'SiteEngineer' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SiteEngineer' failed to provide an identifier", id);
        obj = siteEngineerRepository.findOne(id);
        boolean modified =  dod.modifySiteEngineer(obj);
        Integer currentVersion = obj.getVersion();
        SiteEngineer merged = siteEngineerRepository.save(obj);
        siteEngineerRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'SiteEngineer' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void SiteEngineerIntegrationTest.testSave() {
        Assert.assertNotNull("Data on demand for 'SiteEngineer' failed to initialize correctly", dod.getRandomSiteEngineer());
        SiteEngineer obj = dod.getNewTransientSiteEngineer(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'SiteEngineer' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'SiteEngineer' identifier to be null", obj.getId());
        try {
            siteEngineerRepository.save(obj);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        siteEngineerRepository.flush();
        Assert.assertNotNull("Expected 'SiteEngineer' identifier to no longer be null", obj.getId());
    }
    
}
