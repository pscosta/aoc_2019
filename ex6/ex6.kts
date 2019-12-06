fun main(args: Array<String>) {
    val input = args[0].split(",").map { it.split(")") }.map { it[1] to it[0] }.toMap()

    val checksum = checksum(input)
    println("$checksum")

    val from = input["SAN"]!!
    val dest = input["YOU"]!!
    val shortestPath = pathToRoot(input, from, pathToRoot(input, dest, HashSet()))
    println(shortestPath.size)
}

fun checksum(input: Map<String, String>): Int {
    var count = 0
    input.forEach {
        var sat = it.value
        while (true) {
            count++
            val upper = input[sat]
            sat = upper ?: break
        }
    }
    return count
}

fun pathToRoot(input: Map<String, String>, from: String, path: HashSet<String>): HashSet<String> {
    var next = from
    while (true) {
        if (path.contains(next)) path.remove(next)
        else path.add(next)
        next = input[next] ?: break
    }
    return path
}

main(arrayOf("X9V)9CS,5S8)T3W,R5J)B4C,4H4)YNF,H9L)SDK,MZ1)HQ7,5B5)R6N,GQV)MXT,95Z)TM5,KQH)KLY,TQX)BJM,GW5)TK8,CZ4)G3X,GP4)D26,S9X)SZG,QW8)1L9,D9Z)44M,KP1)W54,77T)HVM,X24)LW5,28V)ZL4,ZG4)DL6,HXW)YX6,S8K)N2D,M3V)RHG,RL9)NRL,MWS)FLB,2QY)SQC,K2V)Z2C,PGQ)1FW,QCT)NYG,B84)8CT,ZPY)7RB,456)JFD,D2C)WXN,R5S)9PJ,H1D)LCY,QJR)N65,C6D)KKD,QY8)GCH,NGQ)CMG,V4H)H4L,2XV)R6S,J87)3KF,891)631,WQ8)GJZ,CX8)Q4L,P79)P4S,4JB)KTS,VV9)PPD,RNM)C4S,5W6)5NH,5KZ)WQ8,5NH)CYF,BV6)768,NT3)S2X,184)3SN,54Z)PQN,HPL)K24,TRL)PW9,7ZK)CDN,JFD)6K9,V6K)XL1,KTM)795,ZNY)G63,39Y)DJ2,CL8)5XP,YBQ)NXN,X62)18Z,3SJ)F3X,FSQ)D5J,5KZ)PGY,91X)BRW,SP3)KM5,HVM)91J,6Q6)HCT,6YN)HXW,TM7)LDX,CC2)FL1,Z7H)3ZC,XJW)2HY,5LN)5B5,WSB)XZL,474)BR8,JQ2)6YD,8LZ)4DN,84F)X62,HMS)G9D,Y6Y)811,CDL)J5Z,DZT)V5S,9D5)4N9,SJT)89J,2B3)WMC,KD9)9FM,ZLN)M24,M7C)HF7,HVB)FKY,VTR)3SV,JCT)96P,KM5)DBX,HMY)C1T,6P5)8J9,LXX)8ZF,LSH)SVS,DC3)TKV,NSX)VFC,W1H)456,R92)8ZV,66N)SCP,WPH)24K,FLB)1K6,C8V)5CP,VKH)N8D,QNM)4VQ,C57)N1W,869)MN5,NZR)6WY,FC5)HXY,YQC)RQ1,L2V)DWF,1V8)FCK,3WN)WYR,1K5)Q11,4JY)KL3,PL7)GJ8,4DY)869,XQ2)6P5,CV3)2WP,PW9)628,8SF)G9Y,WXG)QWN,6M6)Y2P,7VQ)HL2,8R2)BGR,W8Y)VCR,7ZB)97R,GSK)BRB,ZWK)CDC,K8F)W8Y,J9T)5WW,3MV)2QY,KDL)WJL,Y5J)2PK,XMB)7FF,7Q4)QHY,9LY)5QR,XCM)HGW,GK7)CYD,JMC)PSL,62D)HJR,BBC)SD8,BSH)8FC,2LX)ZF8,TK8)DN6,1FK)4V7,D42)TJV,3K5)2BD,H2M)QHR,CGS)4YS,8FC)G7M,RFZ)8HH,BBX)S6G,H6F)TL3,N5Q)XQ2,J66)BGK,MGZ)PWR,T52)QBH,32D)RQ4,RRT)7N2,88Y)RKT,TJK)7GN,V8D)3PJ,P44)K5Y,JZN)MJ2,KQB)KQF,6XC)K8F,5XP)9ZK,H67)JL2,K9Q)P5W,DBX)X9V,KNP)N42,PX8)9H5,GLC)CGZ,LKL)8DY,41B)BV6,1FW)WNC,DHD)D13,LLL)2R1,JG1)1V2,JQ9)7VP,1V2)43Z,PSV)M3V,P1F)Z33,LLK)LXX,RXJ)X3S,G85)SL9,X8F)C3V,G8Z)7QV,4DW)6KD,DRW)T27,HXY)N93,KJY)T8J,96P)P6H,W8K)94V,WTK)FGJ,83T)75F,2WP)LRL,PVK)LKV,T32)XFK,SGV)ZPL,QHR)N5M,J2P)BKC,ZKJ)P51,541)8KV,6VY)SLZ,7K7)KPV,NLD)693,BHP)V3X,48K)NCC,WS1)68V,8LW)VJP,68V)LK9,H7H)Z2Q,PXG)XFY,2TG)GL2,Z96)F4L,29D)PZF,G4X)D25,YZ8)VC8,35H)D1T,MD1)YFC,1Z7)S88,7S9)6PZ,5RJ)ZT2,1S2)CKC,7PV)Q5R,G5X)8YL,CS5)XKH,24K)2T8,Z4S)6FH,VY7)6FV,NYH)L9M,W3L)YM7,CH7)2DV,YJJ)4DL,2NN)1TR,N8D)GM6,97R)XYM,PKD)11M,3VF)4YC,1L6)PVX,3NL)C36,YTZ)K68,TMN)45L,SKW)G8Q,YM7)R5S,71J)LCP,N4M)FGZ,6PJ)JJL,CY3)FLY,DL9)Y4D,PN7)42F,Y3S)SR7,LDX)LP9,CDN)C3R,6XC)5YG,4H4)QY8,94V)Y3S,DRB)R5R,8DY)CDJ,GXX)4Y8,8H4)71J,6FH)SGQ,G9Y)YZM,MJ4)ZSQ,HM3)BT6,RS9)DZT,ZVZ)BCH,T9P)2YT,MG5)KPC,YPW)KK8,TPB)RP1,4P2)DLZ,8RV)9MB,C52)HLN,LF5)6N8,MZQ)M4Q,TM5)9Z7,K68)4QW,C8Z)D68,W54)7SW,QC4)YPR,MRH)HWJ,27Z)8N5,BGR)6FS,TT8)TRY,54N)TGZ,PWP)J5C,3NP)P4Z,9H7)3GL,CL8)P8X,R8C)C7R,LDC)3SR,CKW)N9P,VC8)2DK,SDV)9YB,PMJ)3Y9,KVV)4CV,7SL)K2R,JWR)GJ6,ZMC)HCB,TJV)LS6,2PK)ZVC,Q4L)81Q,VK3)X8F,ZRS)L8J,MB3)GBC,Z33)T32,4Z6)3CK,J33)G4G,YZR)JRR,3SR)33X,QGV)PS4,43Z)3L5,6M6)9WP,RRX)XHZ,HLN)XF5,P6H)SRJ,L1Q)V29,ZMW)H44,KJ9)14Z,K5K)LL1,2FY)KKJ,9NW)8S5,55Z)SJ7,JNC)NGQ,KKS)VRZ,FQ2)TMV,FGV)HJ2,8DQ)VWK,FKY)TBR,ZSQ)CDW,BQ9)GW5,5K6)GXX,Q32)FCC,WJ8)8FR,LJG)997,9Y9)F18,GPR)9F4,M9S)7ZK,SZG)Q7S,2RL)Q2L,3CT)M6G,VNF)66Y,HL2)MD7,XKH)HDD,298)78M,GCL)78R,71Q)XV6,3NL)J7V,LS6)3SC,BDF)ZPY,Y2P)JZ1,4DN)9XV,HDX)58Y,YJP)7PV,C8Z)GLC,8FR)HY1,HQK)V1B,LK9)Z11,Z3K)TWV,TBR)G6H,RGR)F8D,XBM)F8Z,46T)WVG,3QT)XGS,SJL)J3B,FLH)LBX,PSL)T15,KKJ)RRX,KTH)VJD,89J)LDC,33S)GLJ,VQ2)T25,5JR)8Q3,R2N)4MH,V4Z)Z3K,68L)VXK,X5M)NSN,HCT)KQH,DCG)QLB,HDD)BW1,F24)RHL,PPQ)9LY,F3G)QM4,4RC)F41,4YC)29M,LW5)WH9,ZZD)M44,QT4)SBD,5V4)GMB,JCJ)1ZK,JWR)2BC,WXG)T3V,G9Y)YPW,X29)WVB,SD8)7T2,LLW)GL1,THN)55Z,2P7)3Y2,PWK)3K1,SKC)T5M,3V1)5SZ,82T)T9P,7KZ)3K4,XPQ)QR8,7D5)KSC,VQ2)JZN,FMD)9M9,ZPL)ZCY,Q4G)WZ8,XVF)GZZ,PHZ)YJP,616)XPZ,SVW)P3X,33X)4NX,V99)K2Q,BDH)85T,3NB)TL1,LP9)C52,L9M)9SR,GCH)LK5,65Y)ZBJ,9WP)YGQ,2W4)LMN,QZ9)LLW,GMB)35Y,5ZT)VV9,34L)1FK,PPM)Y9X,6RS)3CJ,XC4)Q34,JZX)WGL,D1H)7VQ,FQ2)1F3,ZFF)FMD,F44)LWK,631)ZNY,PQN)GYH,FCC)J9X,V6Z)8NM,89K)2MH,QXX)4Z6,2NN)TPB,HBT)HHC,L2V)JWF,K2Q)8H1,D25)2C7,D4T)BXS,VGR)NN1,HKY)678,Q7V)9H7,PYC)G9B,TJL)KX5,SS6)N1C,VWP)RL9,H5W)YQ1,WLZ)YBZ,QJ5)Z7H,HS6)N2L,WN1)LFL,78R)QCT,GJ6)Z59,JNZ)WT2,MV8)H4B,DJ2)W3L,N4P)7ZB,LX9)S7T,ZRC)B39,QDZ)GVY,YCK)8Z6,DJN)X4N,NPK)3XG,61Q)CC2,P8X)XQD,TZB)7QH,LBC)55J,BCV)WFH,JJN)KP1,9VZ)LH4,811)QRX,5CF)DRW,MJH)NWX,5HN)XF1,26P)YJR,M4Q)YB1,LZB)P21,8B3)3G3,PKK)BBK,P16)28V,TM5)2B1,FXD)B5L,W5C)YW6,BN7)P16,T8K)NT7,XV6)DV7,QTM)6H4,1TR)DGH,CKN)DGF,Z6V)52T,FY9)K2V,HY1)KT9,R8H)29Q,2CL)YY6,J3B)ZMB,RMQ)4WR,L3C)BBG,WQ4)982,7RB)YT5,9M9)D9K,83F)BYF,ZLN)YS8,BR8)W8K,HG2)W2T,WG5)8HS,G77)1HW,NXN)KS4,PJV)D11,FYD)GM3,SDK)F8W,HCZ)9XC,VRS)9R2,NTS)NPC,7W8)LLT,GM6)5CZ,615)4SR,HRQ)CN6,GY1)JHP,1MV)KYC,T25)2K8,4DL)D1D,F7Q)74W,1L9)XF2,4KY)3NP,Q18)616,XQD)L9F,RCK)589,JCJ)9GM,LFD)QDB,NPC)JDM,P42)JQ2,CJX)RS5,P5F)PXG,79V)QKL,WS2)FXD,8M8)4LQ,CKJ)7R6,QCM)XY3,CYF)FJC,TZ8)RFZ,6KD)LD3,MFZ)RRT,RHS)4DY,XFS)J9M,F1H)6H3,TL3)BH5,XLH)QX9,VS2)61V,DWW)PRM,1ZK)8L6,ZW9)FM1,635)S4X,9MK)PN7,RZ5)Q9M,LKB)95Z,8YL)B1B,D8K)V6Z,BZ6)83T,6SH)4ZF,C8P)GTR,26L)XCM,5CZ)XJS,6Q2)7H4,2MH)H2K,M44)BWR,XL1)VT2,8WN)CQ2,FGC)MG5,X3Z)3K5,TKV)3CW,SD7)RF2,KJY)N3X,R5S)S62,C5B)Z2Z,QZF)M8H,5YH)YQC,S4X)N96,LKL)YXL,YPR)Q21,SYG)N2S,7R6)57J,6H3)71F,MVL)RZT,7CB)5YH,LMN)451,FGJ)TQX,24M)RMQ,1TR)LM9,1K6)1JH,68S)Q15,X77)23B,DG7)SVW,DPR)KJ9,BDV)ZDC,44W)2P7,VZ8)KDL,XMB)8B3,3SC)3KC,84L)WPR,6CR)6Q6,ZT2)N7L,42F)NPK,G9R)CS5,QXV)R6R,9ZK)QTM,NFP)7M7,ZRG)KJW,G63)P65,C7R)TCY,TBX)5V4,CYX)71M,D3F)7LM,LKP)2JL,CDT)7HW,6H4)1WM,51S)JKJ,X3P)SKW,628)NT3,7Q9)KG3,8KV)KVV,DL6)LT3,H44)HMS,T27)Q4G,N8F)HB4,CYD)W2V,6XM)FFL,8FR)PFN,7W8)NJW,2DS)FVQ,79G)VK3,LT6)93L,79M)1K5,WKK)B3Q,F29)1C8,YNF)1J7,2BD)1MY,1JH)5C5,2RC)FTL,6NW)ZXJ,VXP)SH8,TMY)9HR,QYP)NLD,PQD)WS1,75F)JQ4,R2R)ZFF,Z9L)VZ3,L9F)X5M,MNG)X52,JJL)N8F,JRH)BZV,5GX)R96,PZM)6L5,8T4)RNM,S62)8ZC,7FF)7KN,QBH)4F3,G74)Z83,SRJ)JRH,K92)CDT,D55)PHN,DZT)STB,G8T)ZWF,Z11)MD1,4WJ)KFX,XQK)NZQ,G6H)2S8,5G5)ZRG,JBV)LKQ,8CT)JHD,14Z)J33,R5R)C5N,K24)V95,GWT)C8Z,55J)8M8,9QW)W1H,RSB)JCT,WWX)Z52,TMY)ZRS,GZZ)BVB,2P7)8SV,ZQR)X7V,VWX)DJN,2H4)VJ3,M1P)QXX,2BC)P7Q,1G2)JG1,GBW)JNQ,YPR)8PY,N3Z)JSZ,F82)ZB1,SGQ)L9J,QRX)7S9,4MH)T8K,4SR)1YD,FD8)1KF,NYG)LKL,JMC)H2M,71Q)24M,N2L)SXJ,Z15)M5V,KG3)BCM,GJZ)TZ8,PKT)JJN,BR8)2MG,X5H)7WT,H43)14X,H9J)XJW,T6F)47R,BCH)69B,DRR)V7G,4LQ)RCK,8C1)TJK,FXS)K9Q,C8B)ZKH,X7V)6SD,4SF)9YD,4VF)HQS,Z59)K43,YGL)CL8,BQQ)FD9,9QY)298,4KN)62P,CB1)LXC,GGY)HVB,8PY)PMJ,6HP)TTN,3D8)M2Y,QCT)RZ5,8ZV)3CT,WNC)48K,L7Q)HG2,698)KTQ,6CV)ZKJ,FJ6)ZQ6,MKY)G8Z,YNF)RPC,MKG)LQ9,4SX)QDH,HQS)G81,78M)615,3Y9)DJH,P2B)VXC,39N)V4H,3CW)37T,KDX)MHS,H16)MVL,ZB1)QWM,4DD)XMM,664)RX8,BN4)VWP,3SV)4BR,HF7)2HW,N4S)1G2,JMJ)5BB,J6W)TXW,8Z4)ZGQ,1TJ)CMP,Q5F)D9Z,4WR)7KZ,VZ8)WQ4,7KY)47S,L8J)4H4,X1T)YVS,45Q)ST2,ZMW)YNK,VRK)T52,MHS)1GH,QCM)VG6,G8Q)FLZ,LCY)Z62,FM1)WLZ,RYB)5S8,CGZ)S8K,VG6)YR1,474)G26,BX9)SGH,F8W)DHD,4QM)G8T,PD9)NMT,KL6)CNY,MW4)C1K,TT8)SMX,4YS)3NN,7VP)1H3,YQC)9KC,TBX)ZVW,CDB)Y2K,PSV)1TP,Q7J)X3Z,COM)YKL,6YD)LXH,RP1)XBM,J9M)PHZ,M8H)71Z,MQ5)7SL,NH2)DC3,696)BQ9,VG6)MW4,YXL)FTZ,C35)7MJ,N65)769,VTV)3XR,LM9)Q92,ZSQ)Q73,HXY)LHP,V29)ZDN,35Y)4P2,5T2)BG6,WZZ)VTV,X24)WTK,P42)2X5,XMM)FQN,3NN)NYW,Q2D)3NL,7KN)VMX,5GK)JL4,Z9L)2DX,LXJ)H16,5Q4)KKV,BG6)PX8,X3P)5T2,37T)3LR,TVY)69M,V5Y)XZ2,B4C)Y88,6TJ)CY3,QLB)L9T,JVY)ZM9,VSK)XQM,Q4G)V6P,944)PTS,MR2)TWS,QP2)PZT,58Y)QW8,SZG)VXP,7GN)5RN,XL1)G9R,NRW)GSZ,YZM)D2C,6DW)84J,HDR)9QY,SY9)JHG,T1D)JPB,B1X)VGR,JHP)5YY,LZ4)QCH,G4G)CBD,WH9)KJY,STB)2L8,HQL)CRY,C1T)N4S,PFN)GRS,VJD)KNL,4VQ)JVY,TF5)P79,LT3)BHP,ZHF)W5C,PPM)7Q2,1KF)4KY,QKL)D9T,1M4)R9S,FXD)TCG,YR1)V84,QCH)PGQ,SH8)15Y,BCR)T2M,GJ8)6RS,PZ5)GS8,PW9)696,99H)14T,BGK)TZB,5DX)P6W,C36)455,9XV)DRR,L43)C4G,H4L)CKN,Z83)XMB,8ZF)2FY,2X5)183,LKQ)YBQ,1N4)DL9,TWV)26L,D68)8RR,V5S)7Q9,6PJ)8RV,SXJ)61Q,FZW)N3Z,LH4)QGV,GSZ)2GF,V3X)3Q3,Q9M)39Y,5SZ)Y5J,PX4)SZQ,KX5)SGW,WVB)QS6,CWJ)8LW,39K)GY4,ZRM)PLK,K1S)RCQ,4F3)D1H,LXC)V2N,M6G)MB3,Q11)64X,GYH)D2M,1H3)VP6,JQ4)7K7,WZ8)WKK,BNR)1N4,74S)H5W,YS8)Z4S,B5L)2MZ,TNS)MWS,WQV)RWH,VWK)X66,WYR)BNR,T3Y)F82,FTF)VWX,VCR)X68,CDJ)GK7,TZZ)SF9,W2V)YZ8,VFC)JWR,MPG)BTS,7LM)77T,6FV)7L9,R6S)ZX1,HMH)NTN,PV4)S8Z,P3X)PPM,RQ4)LVQ,S2X)5DN,XXY)8LR,2GF)6XC,CW7)F6M,SS6)JCJ,ZF8)4RC,LL1)S9X,RCQ)SJT,ZL4)P5F,TRG)JMJ,6VJ)1RH,S78)CWG,2YT)R8H,9NP)35H,TSQ)KD9,MRJ)C7L,KB8)LD9,ZDT)SDV,FJ2)T3R,TFS)944,4VF)2W8,BCM)NT4,9RV)P44,69C)D1X,CKC)5JD,ZM9)BN4,RWR)GKJ,K2W)K79,1S9)1DP,B98)BN7,9XC)VDR,D9T)HH2,NJG)MNG,T5B)GVN,QM4)G39,V95)5KQ,WJX)754,LK5)LNH,GMY)5BQ,N3N)GCW,HJR)VMJ,RS5)KTV,PHN)TPD,DNW)V8D,BDF)JQ9,PV4)XVF,RGX)QGB,5DJ)PD4,1XG)1QT,KKD)1JN,PVK)RKN,693)TNW,SF9)FDZ,1JN)WKR,GCW)FRV,DR9)MPM,9GM)BDV,431)248,QCT)5DC,PWP)1BL,7QV)5DX,DJH)G77,982)DNW,HQ7)TMN,7N2)99H,2H8)CYX,SD7)ZVF,3K1)R47,CMP)TT8,XZJ)RS9,LBX)C35,84J)83G,GYZ)68P,CNY)JNZ,MGZ)XPV,FLY)ZTD,3NP)P3D,9LC)1FJ,9Z7)QZF,2RC)PQ4,KTQ)RWR,3Y2)3NF,WWC)3D8,XF1)5RJ,LNH)HCJ,PQ4)R5J,YMC)V4Z,KYC)DCG,8VJ)9VZ,F7Q)CW7,M2Y)YGL,F8Z)6CR,49Y)ZHF,7LH)PMC,KTV)ZQR,TCG)CMC,8RR)FGV,3CK)NZC,Z52)Q18,2QF)SKC,G7M)1WN,L9T)79V,WPR)BDF,MZY)Q5F,TWS)WZH,TCM)TLF,795)GYZ,F6M)SVN,B3X)X3P,CMG)X24,K2K)BSH,XTL)PXR,NN1)1MV,QHN)MGZ,BRW)ZW9,TT4)CB1,85V)69C,YJR)4KN,5CP)MZQ,997)ZQQ,3W9)K1J,DQN)CV3,BT6)SWK,P4S)WJX,S6G)KRF,L9J)MJ5,W1H)W7N,3KF)26P,5WW)HKY,C6N)VYL,WZ8)B98,D7C)H7H,SZQ)FTJ,1FJ)X5H,FQN)BDH,ZFF)RHS,YS8)D9B,YX6)WXG,GXX)6Q2,PGD)SSM,CDT)CKW,2W8)2W4,5DJ)JB9,1TZ)H9L,YKL)6QX,YNL)R2R,3KL)9MK,3CJ)3VF,JHG)RQT,TBR)ZQF,XYM)K5Q,12H)TQH,VXK)D4T,BZV)Z4F,4BR)16M,9D4)T6F,7X1)4DD,H39)HQL,8MP)67J,GB8)CWJ,N9P)PZX,BH5)431,VMX)FM8,JNQ)D5P,BGK)1P2,8B3)WJF,GM3)FJ6,RX8)TFX,29Q)91X,QZ2)TCM,T8J)474,ZC7)B3F,79F)44W,64X)ZWK,LRL)XLH,ZQ6)XKN,D96)CX8,2QF)964,MJ2)YMC,TPD)WJ8,Z2H)QJR,4ZQ)65N,JL6)HBT,C4G)33G,RXJ)4SF,M24)GPR,3XG)DDD,VJP)ZVZ,7T2)BFR,C4H)J6Y,9BZ)1XG,S8H)YZ6,SRJ)1L6,7M7)8SN,PQ4)HYL,QML)6SH,S93)7C8,34L)3KL,Q2P)T7C,TNW)TRF,1J7)KND,YNK)CS3,KK8)J2P,D9K)K23,C3V)4JB,YM4)KD1,QBH)PZM,5CL)CZ4,TRY)2B3,PRM)54N,GB8)DH4,811)2CL,N16)7D5,CSK)PW6,45L)MRJ,57J)8R2,JB9)LF5,P6W)8C1,ZXJ)KDX,JHD)2XV,K1J)TZZ,1F3)2BY,K79)2H8,QXX)QZ9,C5G)1M9,JPX)GY1,Y7F)39K,X1T)2H4,T6F)G5X,DCQ)WQV,HL2)PWP,F18)6DW,2K8)6TJ,QGB)B3H,69B)ZMW,C4H)635,1RH)KDK,ZVZ)7WL,3YX)QKM,QDH)KPM,BKC)WN1,6L5)THN,5TL)SJL,W86)TMY,Z4F)97V,YBZ)J9T,4Y8)PVK,HPK)PSV,9MB)D3F,TSH)32D,XGG)BQQ,NYW)1G1,QX9)1S3,DN6)QZ2,F8T)PWK,P5W)FYD,3D8)9NP,GTR)MKY,1WM)C8B,QR8)TRL,3N5)CQM,VMJ)WSQ,SP4)9D4,G9D)667,X66)RYB,JL4)YTZ,15Y)GQV,RGP)5PF,BBK)6YP,VRZ)ZWL,H9M)1BT,B3Q)WB6,BX1)6CV,Y2K)N3N,T47)SYG,978)Y6C,6YP)W2H,14X)4ZQ,8S5)5HT,T7C)QDZ,QHP)9DC,2MG)N16,J5C)4CK,LW5)62D,WVG)SRD,8Q3)83F,26D)BBC,WKR)WG5,DDD)5ND,ZWL)D6G,2DK)SP4,4LH)LSN,VYL)7TF,2R1)5LX,16M)5Q4,WCM)X77,SBD)Y63,V84)8H4,GL1)DQN,KFX)M1P,PLK)XC4,5QG)TJL,2WR)HLB,KKV)SBY,5LX)BX9,DSQ)9QT,HH2)8SF,3L5)46T,2T8)XFS,RQ1)27Z,3NF)2RL,8HS)F29,1FC)PKT,Y63)QYP,5FG)ZLN,Z2Q)RW8,2S8)P56,C7M)891,JYY)ZRC,9SS)C8V,MJ4)FZW,M66)PPQ,TLF)VRS,H79)T1D,5P5)WCM,JCD)3MV,795)V6K,Q92)XZJ,93L)P42,XQM)LLK,N42)GMY,FD9)32Y,QLH)3NB,8N5)1Q4,J9X)L43,8ZF)MZY,D11)KTH,YLM)GH1,4NX)ZDT,958)L2V,ZSL)K5K,Y6C)9ZM,PWR)C1J,CSK)QYB,JWF)W86,9F4)MV8,SJT)9D5,V1F)Q7J,8SP)16X,TCY)BF8,T5M)TT4,91J)HB7,1G1)K4X,CWG)PZ5,WXN)NBJ,F1V)ZRM,ZX1)X1T,GL2)VSK,FPM)RGR,4CV)5LN,8ZC)VTR,7TF)HQK,5YY)8DG,9DC)H79,Y9X)5TX,LT6)3YX,GFJ)Z6V,CMP)S93,65N)X33,D9B)S78,3PJ)3WJ,WSQ)ZC7,9YD)MZ1,8H1)NRW,YB1)L3C,STC)LXJ,G39)RR3,9HR)GBW,B3F)9SJ,8G8)9NW,6CP)VKN,KRF)8LZ,2HY)R8Y,WB6)C4H,CQ2)664,3WN)6YN,YW6)XPQ,768)978,D4P)9QW,KQ5)BCV,7MZ)XW1,7WL)C6H,67J)NTS,FGC)F27,ZSZ)65S,7WT)B17,P44)5GK,KS4)2NN,1P2)GSK,KTS)T47,5TX)L7Q,WT2)L3D,5YG)SG2,TS4)LT6,6PZ)KKS,BW1)5ZT,PW6)F1V,1MY)5TS,ZDN)1S2,4B6)9XP,CV3)3LM,9QV)Y6Y,74Y)QP2,SLZ)KQB,85Z)R8C,9GQ)C6N,BF8)29Z,HB7)D4P,PZT)5KZ,V8D)8DQ,XW1)4LH,9CG)85Z,B1J)MPG,71Z)PKD,1KF)J66,KPM)1ZD,1BL)MKG,1YD)3N5,27M)XXY,62D)YCK,K8B)GB8,RQD)DM6,BJM)TS4,698)TVY,7MJ)2RC,23B)CKJ,Q15)M66,B1D)PD9,Q5F)QT4,5JF)S4R,DDQ)6HR,2HW)8X5,N93)WSB,FFL)VRK,C7L)R17,9NZ)2LX,BTY)CWF,18Z)9BZ,R17)ZG4,2L8)797,FTJ)JZX,M5V)7X1,TRL)J29,5NF)VS2,4M3)ZFX,PD4)6M6,GWX)SLR,SVN)7Q4,KD1)JYY,6QX)FD8,V6P)MJ4,4QM)H43,Z11)24X,RVD)51S,NBJ)62G,HJ2)LX9,93G)YJJ,K4X)J87,SMX)H1D,C57)JN6,4MR)6VY,LBC)CH7,KND)ZSL,Y5W)Q2D,Z2Z)284,G26)SY9,85T)QHN,YZY)8PW,4ZF)SP3,ZBJ)8VJ,N1W)H6F,RSQ)QC4,9QT)M9S,PZN)JRW,4P2)6PJ,62P)L26,T15)P27,F4L)FGC,HCZ)FC5,T3V)N2B,P7Q)MQ5,FM8)SS6,XFK)5JF,VMX)LZB,KPR)Z6B,RS5)DWW,MBY)YOU,W7N)PJV,2DX)JL6,HCJ)1K9,16X)BF7,FCK)MJH,J16)6VJ,2B1)R2N,T3R)V5Y,ST2)HFS,6K9)WWC,2MZ)K2W,M8Q)QXV,LKS)NYH,8LR)N5Q,YQH)GCL,ZMB)DLJ,5WH)C6D,52T)85V,754)CDL,K23)S8H,VXC)MBH,SJ7)5DJ,XGS)4JY,7H4)49Y,Q73)C57,HJJ)D55,HLB)39V,Y4D)378,LLT)GFJ,HB4)1FC,CRY)RS2,1K9)7KY,FW7)P2B,R9S)1QD,RHG)5G5,TPJ)K2K,7TF)LSH,ZTD)93G,S8Z)GWX,BG6)WS2,ZKH)NSP,YM2)F3G,65S)PX4,B32)D7C,GC4)B32,NRL)SAN,9ZM)NZR,SSM)XQK,2BY)VZ8,KTH)RSQ,KNL)4M3,BRB)3QT,FVQ)84F,3W9)HPK,K24)MR2,NCC)79G,Y63)NHN,VFD)RSB,24X)LKB,D4P)YM2,JZ1)JMC,BBH)4ZV,G3X)YSW,MD9)QCM,NSN)Q62,JRR)3SJ,SRD)9RV,B17)84L,C1J)9LC,356)QML,BV6)G74,8J9)LJG,1TP)GP4,3LR)Q7V,GPW)D96,YR7)9NZ,VKN)7W8,TWS)79M,D1X)B1X,XPZ)8G8,NSP)41B,KJ9)RGX,XFY)68S,QNH)JPX,YZ6)PJR,71J)MBC,74W)YZY,SSM)5HN,XPV)R92,8HH)2DS,LCP)YZR,3Q3)W1J,11M)KDG,X74)SGV,9XP)TRG,D55)P1F,WMC)1Z7,8HY)1M4,4NY)YM4,9KC)M8Q,R17)FLH,DGH)NFP,62P)H39,8Z6)82T,RP1)F3Q,NWX)8MP,SBY)LNW,LQ9)Y5W,9CL)YWJ,XY3)V99,FDZ)RPT,ZBL)3W9,R6Q)LJT,T3W)3FR,STB)962,D1D)6D8,J5Z)RWP,6BJ)5CQ,RKT)NJG,Q62)541,YT5)T5B,CN6)FXS,FJ6)7CB,44M)88Y,29Z)HM3,4ZV)12H,JN6)TSQ,NJW)L1Q,J29)X7L,TGZ)45Q,V7G)PGD,TFX)C5B,221)VDJ,5PF)FTF,H2K)F24,QWN)5CL,183)KQ5,PZF)9P9,YQ1)D79,Y88)FLQ,LVQ)8SP,MPM)K92,1GH)88V,6TR)6HP,Z6B)5JR,NHD)ZZD,LD3)NBQ,TJV)4QM,WH9)F8T,YJJ)BX1,L26)RXJ,NXG)BTY,6D8)X1L,D9K)TNS,3ZC)H33,TXW)VHN,GKJ)Z9L,V1B)FW7,71F)TX3,HYL)TVD,RQT)R36,YY6)K76,S88)QLH,SR7)2WR,NZQ)SD7,CBD)HDX,MJ5)4K9,LFL)F4D,HHC)4B6,GLJ)9CL,PFN)ZBL,5CQ)J6W,XQM)JRC,GS8)4MR,VDJ)68L,69M)R7V,VZ3)ZSZ,2JL)B1J,JSZ)DPR,Q21)39N,SGW)31R,36P)X9P,B17)5FG,2NY)FQ2,6FS)HMH,GH1)CSK,7V7)5WH,6KD)DD2,LWK)DCQ,88V)MD9,4RC)DG7,4N9)34L,81Q)VQ2,P3D)7L3,6CV)DRB,VT2)GWT,YVS)HJJ,XJS)D8K,X1L)LKS,ZVC)4SX,39V)4QT,R47)MKH,B1X)XTL,47R)36P,FTZ)K8B,CS3)5GX,DCG)8WN,DWF)G4X,VP4)CGS,PTS)PV4,J6Y)1TJ,6WY)Z2H,B7T)5P5,Q5R)NSX,GRS)CJX,HPM)1S9,KDG)KB8,N2D)J16,LKQ)C5G,YWJ)ZMC,9YB)PQD,ZX3)BCR,31R)37J,X68)KL6,8SV)BZ6,LJT)5NF,NGQ)C7M,83G)356,YFC)F7Q,H4B)LLL,SWK)RQD,66Y)6NW,8LW)GC4,664)33L,WJF)JBV,RWP)VP4,6N8)4NY,8HH)Z96,32Y)4TN,6SD)MRH,667)JXJ,P56)K1S,J6H)JNC,YGQ)7MZ,D5J)RGP,5KQ)X74,D79)ZX3,NBQ)XGG,RHH)N4P,ZGQ)T3Y,5RN)YQH,X5M)PNP,71F)HS6,LKV)YR7,S16)FPM,B1B)LZ4,N1C)Q32,BTS)BBX,WMC)B3X,LSN)12X,7L3)6CP,R7V)Y7F,ZDC)H67,BDV)HPL,W1J)7LH,4KY)DSQ,KQF)184,9SJ)27M,PZ5)STC,X7L)C8P,BFR)8T4,DM6)HRQ,GVN)74S,RWH)V7W,CJX)TM7,NT4)HT2,5DN)132,Y49)C1H,8K2)54Z,ZWF)H9J,N5M)NXG,769)GPW,Q2L)DR9,X4N)QNM,3GL)B7T,8PW)5XV,37J)PL7,C1H)PZN,PZX)VKH,RF2)B1D,HT2)X29,SG2)65Y,VJ3)DTW,P51)HMY,FTL)V1F,N2S)9P2,455)5TL,JL2)RVD,GW5)H9M,VHN)TM9,C6D)VFD,GBC)MWX,DV7)TF5,WGL)VNF,WJ6)BBH,PXG)CDB,ZWK)5CF,BSH)26D,2T8)RHH,33L)JCD,LXH)1TZ,X9P)5W6,FLZ)74Y,NT7)2HF,P4Z)PYC,DLZ)WJ6,TPB)KZT,MD7)FJ2,14T)4DW,ZB1)698,F4D)79F,LNW)MYF,JRW)6XM,ZVW)MFZ,CDW)4VF,1WN)NH2,QHY)YLM,XFK)3V1,DLJ)LFD,SGH)89K,N7L)F44,7QH)HPM,SLR)QNH,ZQF)9SS,VKH)9GQ,5GX)2TG,WS2)2QF,R6R)RDZ,132)8RP,D1T)29D,SGW)4WJ,ZVF)WPH,RKN)R6Q,T2M)PKK,HDR)71Q,5BQ)Y49,962)QHP,NXG)LBC,FLQ)8HY,39N)WWX,XKN)HDR,VDR)958,K43)66N,FGZ)Q2P,RS2)D42,8X5)WZZ,3NN)8K2,B39)GGY,G9B)J6H,8SN)138,KPC)FY9,PVX)TFS,G81)S16,BWR)QJ5,TM9)5K6,33G)DDQ,TTN)7V7,9P2)9CG,7HW)B84,5DC)YNL,PMC)LKP,CRY)G85,P65)6BJ,9CS)TBX,LHP)5QG,KLY)BR2,HCB)Z15,X77)KPR,KSC)W6S,964)TSH,8RP)1V8,9FM)2NY,BYF)MBY,LD9)FSQ,3XR)33S,9PJ)8Z4,N3X)3WN,8NM)NHD,MYF)M7C,F3Q)221,N7L)KNP,C5N)6TR,71M)J84,97V)TPJ,CWF)9QV,5DC)HCZ,VP6)17S,CMC)KTM,356)N4M,DTW)VY7,X3S)F1H,RHL)9Y9"))
