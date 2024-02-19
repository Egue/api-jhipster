package com.comunicamosmas.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.comunicamosmas.api.service.IGlobalFunctionsService;

@Service
public class GlobalFunctionsServiceImpl implements IGlobalFunctionsService{

    @Override
    public List<String> generateIPRange(String start, String end) {
         List<String> ips = new ArrayList<>();

         String[] startParts = start.split("\\.");
         String[] endParts = end.split("\\.");

         long first = ipToLong(startParts);
         long endi = ipToLong(endParts);

         for(long i = first; i <= endi; i++)
         {
            String ip = longToIP(i);
            ips.add(ip);
         }

         return ips;
    }

    private long ipToLong(String[] ipParts)
    {
        long ip = 0;
        for (int i = 0; i < 4; i++) {
            ip += Long.parseLong(ipParts[i]) << (24 - (8 * i));
        }
        return ip;
    }

    private String longToIP(long ip) {
        StringBuilder sb = new StringBuilder(15);
        for (int i = 0; i < 4; i++) {
            sb.insert(0, Long.toString(ip & 0xff));
            if (i < 3) {
                sb.insert(0, '.');
            }
            ip >>= 8;
        }
        return sb.toString();
    }
    
}
