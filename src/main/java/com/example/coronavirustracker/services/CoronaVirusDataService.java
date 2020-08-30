package com.example.coronavirustracker.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.coronavirustracker.models.LocationStats;

@Service
public class CoronaVirusDataService {

	private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

	private List<LocationStats> allStats = new ArrayList<>(); // list of location statistics
	
	public List<LocationStats> getAllStats() {
		return allStats;
	}
	
	@PostConstruct
	@Scheduled(cron = "1 1 1 1 1 1")
	public void fetchVirusData() throws IOException, InterruptedException {

		List<LocationStats> newStats = new ArrayList<>();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();

		HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
		StringReader csvBodyReader = new StringReader(httpResponse.body());

		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
		for (CSVRecord record : records) {
			LocationStats locStatVar = new LocationStats();
			locStatVar.setState(record.get("Province/State"));
			locStatVar.setRegion(record.get("Country/Region"));
			int lastCol = Integer.parseInt(record.get(record.size() - 1));
			int previousDayLast = Integer.parseInt(record.get(record.size() - 2));
			locStatVar.setLatestTotalCases(lastCol);
			locStatVar.setDifferenceFromPreviousDay(lastCol - previousDayLast);
			//System.out.println(locStatVar);
			newStats.add(locStatVar);
		}
		
		allStats = newStats;

	}
}
