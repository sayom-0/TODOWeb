package org.example.todoweb;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/todo")
public class TODOListServlet extends HttpServlet

    private TODOListRepository repository;

    @Override
    public void init() throws ServletException
    {
        repository = new TODOListRepository();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String action = request.getParameter("action");

        if ("add".equals(action))
        {
            String description = request.getParameter("description");

            if (description != null && !description.trim().isEmpty())
            {
                repository.addItem(description);
            }
        }
        else if ("delete".equals(action))
        {
            int index = Integer.parseInt(request.getParameter("index"));
            repository.deleteItem(index);
        }
        else if ("clear".equals(action))
        {
            repository.clearList();
        }
        response.sendRedirect("todo");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<TODOItem> items = repository.getItems();
        request.setAttribute("items", items);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}