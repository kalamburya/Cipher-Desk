package decrypt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import decrypt.model.HistoryRecord;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryService {

    private static final Logger log = LoggerFactory.getLogger(HistoryService.class);
    private static final int MAX_HISTORY_SIZE = 50;

    @SuppressWarnings("unchecked")
    public List<HistoryRecord> get(HttpSession session) {
        List<HistoryRecord> history = (List<HistoryRecord>) session.getAttribute("history");

        if (history == null) {
            history = new ArrayList<>();
            session.setAttribute("history", history);
        }

        return history;
    }

    public void add(HttpSession session, HistoryRecord record) {
        List<HistoryRecord> history = get(session);
        history.add(record);

        if (history.size() > MAX_HISTORY_SIZE) {
            history.removeFirst();
            log.debug("Лимит истории ({}) превышен, старейшая запись удалена", MAX_HISTORY_SIZE);
        }

        session.setAttribute("history", history);
        log.debug("Запись добавлена, текущий размер истории: {}", history.size());
    }

    public void clear(HttpSession session) {
        session.setAttribute("history", new ArrayList<HistoryRecord>());
        log.info("История сессии очищена пользователем");
    }
}