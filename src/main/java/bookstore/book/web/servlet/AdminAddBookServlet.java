package bookstore.book.web.servlet;

import bookstore.book.domain.Book;
import bookstore.book.service.BookService;
import bookstore.category.domain.Category;
import bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AdminAddBookServlet",urlPatterns = "/admin/AdminAddBookServlet")
public class AdminAddBookServlet extends HttpServlet {
    private BookService bookService = new BookService();
    private CategoryService categoryService = new CategoryService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        DiskFileItemFactory factory = new DiskFileItemFactory();

        ServletFileUpload sfu = new ServletFileUpload(factory);
        sfu.setFileSizeMax(100 * 1024);
        try {
            List<FileItem> fileItemList = sfu.parseRequest(req);
            Map<String,String> map = new HashMap<>();
            for(FileItem fileItem: fileItemList) {
                if(fileItem.isFormField()) {
                    map.put(fileItem.getFieldName(),fileItem.getString("UTF-8"));
                }
            }

            Book book = CommonUtils.toBean(map,Book.class);
            book.setBid(CommonUtils.uuid());
            book.setDel(false);

            Category category = CommonUtils.toBean(map,Category.class);
            book.setCategory(category);
//            String savepath = this.getServletContext().getRealPath("/bookstore/book_img");
            String savepath = "/Users/selfknow/program/Heima/code/User/src/main/webapp/bookstore/book_img";
            String filename = CommonUtils.uuid() + "_" + fileItemList.get(2).getName();

            System.out.println(fileItemList.get(2).getFieldName());
            File destFile = new File(savepath,filename);
            fileItemList.get(2).write(destFile);

            book.setImage("book_img/" + filename);
            System.out.println(book);
            bookService.addBook(book);

            req.getRequestDispatcher("/admin/AdminBookServlet?method=findAllBook")
                        .forward(req,resp);
        } catch (Exception e) {
            if((e instanceof FileUploadBase.FileSizeLimitExceededException)) {
                req.setAttribute("msg","您上传的图片超过100KB");
                req.setAttribute("categoryList",categoryService.findAll());
                req.getRequestDispatcher("/bookstore/adminjsps/admin/book/add.jsp")
                        .forward(req,resp);
            }

        }


    }
}
