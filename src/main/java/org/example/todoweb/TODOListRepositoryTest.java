package org.example.todoweb;

import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TODOListRepositoryTest
{
    private static TODOListRepository repository;

    @BeforeAll
    static void setup()
    {
        repository = new TODOListRepository();
    }

    @BeforeEach
    void clearBeforeTest()
    {
        repository.clearList();
    }

    @Test
    void testAddItem()
    {
        repository.addItem("Test Task");
        List<TODOItem> items = repository.getItems();
        assertEquals(1, items.size());
        assertEquals("Test Task", items.get(0).getDescription());
    }

    @Test
    void testDeleteItem()
    {
        repository.addItem("Task 1");
        repository.addItem("Task 2");
        repository.deleteItem(0);
        List<TODOItem> items = repository.getItems();
        assertEquals(1, items.size());
        assertEquals("Task 2", items.get(0).getDescription());
    }

    @Test
    void testClearList()
    {
        repository.addItem("Task A");
        repository.addItem("Task B");
        repository.clearList();
        List<TODOItem> items = repository.getItems();
        assertTrue(items.isEmpty());
    }
}
