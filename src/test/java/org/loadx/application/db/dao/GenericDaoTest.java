package org.loadx.application.db.dao;

import org.loadx.application.db.entity.LoadTask;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GenericDaoTest {

    private Dao<LoadTask> dao = new GenericDao<>();

    @Test
    public void testGetByIdReturnNotEmptyResult() {
        LoadTask task = new LoadTask();
        task.setBaseUrl("testUrl");
        task.setLoadingTime(10000);
        task.setParallelThreshold(5);

        int id = dao.save(task);

        LoadTask item = dao.getById(id, LoadTask.class);
        Assert.assertNotNull(item);
        Assert.assertEquals(item.getBaseUrl(), "testUrl");
        Assert.assertEquals(item.getLoadingTime(), 10000);
        Assert.assertEquals(item.getParallelThreshold(), 5);

        dao.remove(item);
    }

    @Test
    public void testGetAllReturnNotEmptyResult() {
        LoadTask task = new LoadTask();
        task.setBaseUrl("testBaseUrl1");
        task.setLoadingTime(100);
        task.setParallelThreshold(4);

        dao.save(task);

        List<LoadTask> tasks = dao.getAll(LoadTask.class);
        Assert.assertNotNull(tasks);
        Assert.assertTrue(tasks.size() > 0);
    }

    @Test
    public void testSaveShouldReturnTrueAndItemIsAccessibleThen() {
        LoadTask task = new LoadTask();
        task.setBaseUrl("testBaseUrl1");
        task.setLoadingTime(100);
        task.setParallelThreshold(4);

        int result = dao.save(task);

        LoadTask savedItem = dao.getById(result, LoadTask.class);
        Assert.assertNotNull(savedItem);
        Assert.assertEquals(savedItem.getBaseUrl(), "testBaseUrl1");

        dao.remove(savedItem);
    }

    @Test
    public void testSaveAllPersistsAllGivenItems() {
        LoadTask task = new LoadTask();
        task.setBaseUrl("testBaseUrl2");
        task.setLoadingTime(150);
        task.setParallelThreshold(4);

        LoadTask task1 = new LoadTask();
        task1.setBaseUrl("testBaseUrl3");
        task1.setLoadingTime(200);
        task1.setParallelThreshold(4);

        List<LoadTask> tasks = List.of(task, task1);

        List<Integer> ids = dao.save(tasks);

        Assert.assertNotNull(ids, "Records should be persisted");

        for (Integer id : ids) {
            LoadTask item = dao.getById(id, LoadTask.class);
            Assert.assertNotNull(item, "The record should be persisted");
            dao.remove(item);
        }
    }

    @Test
    public void testUpdateReturnsUpdatedRecord() {
        LoadTask task = new LoadTask();
        task.setBaseUrl("test");
        task.setLoadingTime(100);
        task.setLoadingTime(1);

        int id = dao.save(task);

        LoadTask fetched = dao.getById(id, LoadTask.class);
        final String changedValue = "changed-value";
        fetched.setBaseUrl(changedValue);

        dao.update(fetched);
        fetched = dao.getById(id, LoadTask.class);

        Assert.assertEquals(fetched.getBaseUrl(), changedValue);

        dao.remove(fetched);
    }

    @Test
    public void testDeleteRemovesRows() {
        LoadTask task = new LoadTask();
        task.setBaseUrl("test");
        task.setLoadingTime(100);
        task.setLoadingTime(1);

        int id = dao.save(task);
        dao.remove(dao.getById(id, LoadTask.class));

        Assert.assertNull(dao.getById(id, LoadTask.class));
    }

}
