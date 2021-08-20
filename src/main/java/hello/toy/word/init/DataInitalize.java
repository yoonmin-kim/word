package hello.toy.word.init;

import hello.toy.word.domain.Report;
import hello.toy.word.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitalize {

    private final ReportRepository reportRepository;

    @PostConstruct
    public void init() {
        log.info("<== DataInitalize START ==>");
        Report report1 = new Report();
        report1.setTargetWord("{test}");
        report1.setReplaceWord("혜림");
        reportRepository.save(report1);

        Report report2 = new Report();
        report2.setTargetWord("{test2}");
        report2.setReplaceWord("혜림2");
        reportRepository.save(report2);
    }
}
