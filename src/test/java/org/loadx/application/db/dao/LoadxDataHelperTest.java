package org.loadx.application.db.dao;

import org.loadx.application.config.ApplicationConfig;
import org.loadx.application.constants.RequestType;
import org.loadx.application.db.entity.LoadRequest;
import org.loadx.application.db.entity.LoadTask;
import org.loadx.application.db.entity.LoadxEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

@Import(ApplicationConfig.class)
public class LoadxDataHelperTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("loadxDataHelper")
    private LoadxDataHelper helper;

    @Test
    public void testPersistLoadTaskRequests() {
    }

    @Test
    public void testGetLoadRequestsByTaskId() {
        LoadTask task = new LoadTask();
        task.setBaseUrl("test");
        task.setLoadingTime(100);
        task.setLoadingTime(1);

        LoadRequest request = new LoadRequest();
        request.setRequestName("testRequestName");
        request.setTimeout(100);
        request.setType(RequestType.GET.getValue());
        request.setUrl("/test");

        LoadRequest request1 = new LoadRequest();
        request1.setRequestName("testRequestName1");
        request1.setTimeout(100);
        request1.setType(RequestType.GET.getValue());
        request1.setUrl("/test");

        List<LoadRequest> requests = List.of(request, request1);

        int taskId = helper.persistLoadTaskRequests(task, requests);

        List<LoadRequest> requestsById = helper.getLoadRequestsByTaskId(taskId);

        Assert.assertEquals(requestsById.size(), 2);

        helper.getLoadTaskDao().remove(helper.getLoadTaskDao().getById(taskId, LoadTask.class));
        helper.getLoadRequestDao().remove(requestsById);
        helper.getTaskRequestsDao().removeRequestsByTaskId(taskId);
    }
}