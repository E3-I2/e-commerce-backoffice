package e3i2.ecommerce_backoffice.domain.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder({
        "summary"
        , "charts"
})
public class StatResponse {
    private final SummaryResponse summary;
    private final ChartsResponse charts;

    private StatResponse(SummaryResponse summary, ChartsResponse charts) {
        this.summary = summary;
        this.charts = charts;
    }

    public static StatResponse register(SummaryResponse summary, ChartsResponse charts) {
        return new StatResponse(summary, charts);
    }
}
