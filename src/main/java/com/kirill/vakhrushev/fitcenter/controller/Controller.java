package com.kirill.vakhrushev.fitcenter.controller;

import java.time.LocalDate;
import java.util.List;

import com.kirill.vakhrushev.fitcenter.core.ManagerAdmin;
import com.kirill.vakhrushev.fitcenter.core.ReportService;
import com.kirill.vakhrushev.fitcenter.core.Turnstile;
import com.kirill.vakhrushev.fitcenter.model.AccountCreatedEvent;
import com.kirill.vakhrushev.fitcenter.model.Event;
import com.kirill.vakhrushev.fitcenter.model.EventHandler;
import com.kirill.vakhrushev.fitcenter.model.StatInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final ManagerAdmin managerAdmin;
    private final Turnstile turnstile;
    private final ReportService reportService;

    @Autowired
    public Controller(
            ManagerAdmin managerAdmin,
            Turnstile turnstile,
            ReportService reportService
    ) {
        this.managerAdmin = managerAdmin;
        this.turnstile = turnstile;
        this.reportService = reportService;
    }

    @GetMapping("/daily/info")
    public List<StatInfo> getDailyInfo(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam("from")
            LocalDate from,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam("to")
            LocalDate to
    ) {
        return reportService.getDailyStat(from, to);
    }

    @PostMapping("/account/create/{id}")
    public void create(@PathVariable("id") long accountId) {
        managerAdmin.createAccount(accountId);
    }

    @PostMapping("/account/extension/{id}")
    public void extension(
            @PathVariable("id")
            long accountId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam("extension_date")
            LocalDate date,
            @RequestParam("days")
            int days
    ) {
        managerAdmin.extensionAccount(accountId, date, days);
    }

    @PostMapping("/account/entry/{id}")
    public void entry(@PathVariable("id") long accountId) {
        turnstile.entry(accountId);
    }

    @PostMapping("/account/escape/{id}")
    public void escape(@PathVariable("id") long accountId) {
        turnstile.entry(accountId);
    }
}
