package com.example.storeoflove.requests.partners;

import com.example.storeoflove.requests.base.BaseServlet;
import com.example.storeoflove.requests.partners.model.AttachmentResult;
import com.example.storeoflove.requests.partners.model.PartnerResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet(name = "getPartnersServlet", value = "/get_partners")
public class GetPartnersServlet extends BaseServlet {

    private static final String PARAM_GENDER = "gender";

    @Override
    public List<String> requiredParams() {
        return Collections.singletonList(PARAM_GENDER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!checkRequestParams(req, resp)) return;

        String gender = req.getParameter(PARAM_GENDER);

        connectToDatabase(resp, (statement) -> {
            ArrayList<PartnerResult> partners = new ArrayList();
            String sql = "select id, first_name, last_name, short_description, birthdate, gender, photos, video from people where gender=" + gender;
            ResultSet partnersSet = statement.executeQuery(sql);
            while (partnersSet.next()) {
                PartnerResult partner = new PartnerResult(
                        partnersSet.getInt("id"),
                        partnersSet.getString("first_name"),
                        partnersSet.getString("last_name"),
                        partnersSet.getInt("gender"),
                        partnersSet.getString("short_description"),
                        partnersSet.getString("birthdate"),
                        partnersSet.getString("photos"),
                        partnersSet.getString("video")
                );

                partners.add(partner);
            }

            for(PartnerResult partner : partners) {
                String photoIds = partner.getPhotoIds();
                ResultSet photosResult = statement.executeQuery("select * from attachments where id in (" + photoIds + ")");
                ArrayList<AttachmentResult> photos = new ArrayList();
                while (photosResult.next()) {
                    AttachmentResult photo = new AttachmentResult(
                            photosResult.getInt("id"),
                            photosResult.getString("url")
                    );
                    photos.add(photo);
                }

                partner.setPhotos(photos);
            }

            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String jsonResult = gson.toJson(partners);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().print(jsonResult);
        });
    }
}
