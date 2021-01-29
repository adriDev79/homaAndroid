package com.homa.bll.utils;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.homa.bll.asyncTask.AsyncTaskBilan;
import com.homa.bll.asyncTask.AsyncTaskDepenseAnnexe;
import com.homa.bll.asyncTask.AsyncTaskDepenseFixe;
import com.homa.bll.asyncTask.AsyncTaskRevenu;
import com.homa.bll.asyncTask.AsyncTaskSolde;

import java.util.LinkedList;

public class AsynktaskUtils {

    public static void asyncTasksExecute(Context ctx, String dateAccount, LinkedList<ListView> listViews, LinkedList<LinearLayout> linearLayouts){
        AsyncTaskRevenu asyncTaskRevenu = new AsyncTaskRevenu(ctx, listViews.get(0), dateAccount, linearLayouts.get(0));
        asyncTaskRevenu.execute();

        AsyncTaskDepenseFixe asyncTaskDepenseFixe = new AsyncTaskDepenseFixe(ctx, listViews.get(1), dateAccount, linearLayouts.get(1));
        asyncTaskDepenseFixe.execute();

        AsyncTaskDepenseAnnexe asyncTaskDepenseAnnexe = new AsyncTaskDepenseAnnexe(ctx, listViews.get(2), dateAccount);
        asyncTaskDepenseAnnexe.execute();

        AsyncTaskSolde asyncTaskSolde = new AsyncTaskSolde(ctx, listViews.get(3), dateAccount);
        asyncTaskSolde.execute();

        AsyncTaskBilan asyncTaskBilan = new AsyncTaskBilan(ctx, listViews.get(4), dateAccount);
        asyncTaskBilan.execute();
    }
}
