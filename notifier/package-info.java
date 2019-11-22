package com.softwaregroup.digiwave.eip.components.communication.connectors.custom.kicb.notifier;

/**
 * In this package implemented logic of notifying partners about payments.
 * For now exist 5 partners:
 * Razlet.kg	        https://finance.bookit.kg/external_services/ext_payment.php?vendor=elsom
 * Bookit Pseudo	    https://financesecure.kochevnik.kg/external_services/ext_payment.php?vendor=elsom
 * Kochevnik Agent 1	https://finance-nocloud.biletstandart.kg/external_services/ext_payment.php?vendor=elsom
 * Bilet Offline	    https://gate.razlet.ru/app/api/bank/elSom
 * PF APAKE	            https://apake.kg/transaction/goelsom/
 *
 * This partners are merchants. Service should get transactions from DB and send list to the partners
 * in specific format described below:
 *
 * Content type = text/xml. Method = POST
 *
 * REQUEST SAMPLE:
 * <NotifyPendingPayments>
 *   <Token>48feacea447e7d6a6ec672e8d258246f</Token> // Is this token used by partner?
 *   <MSISDNPayee>0905999999</MSISDNPayee>
 *   <TotalRows>2</TotalRows>
 *   <BatchDate>20191121T005805</BatchDate>
 *   <DataList>
 *     <Data>
 *       <RowNo>1</RowNo>
 *       <MSISDNPayer>0905003083</MSISDNPayer>
 *       <Amount>14998</Amount>
 *       <Ccy>KGS</Ccy>
 *       <TxnID>PP191121.0055.C32531</TxnID>
 *     </Data>
 *     <Data>
 *       <RowNo>2</RowNo>
 *       <MSISDNPayer>0905003083</MSISDNPayer>
 *       <Amount>2298</Amount>
 *       <Ccy>KGS</Ccy>
 *       <TxnID>PP191121.0057.C32532</TxnID>
 *     </Data>
 *   </DataList>
 * </NotifyPendingPayments>
 * Each data element represents transaction.
 *
 * RESPONSE SAMPLE:
 * <Response>
 *    <ErrorCode>0</ErrorCode>
 *    <ErrorMsg>SUCCESS</ErrorMsg>
 * </Response>
 *
 * Operating principle:
 * Program is running as a process. In current system this implemented as infinity loop thread. While running it sends
 * request to DB as scheduler, each 10 seconds by default. This request will get all existing partners and list of transactions
 * for last 10 seconds. Then check if response from DB has rows. TRUE => send request to Partner, FALSE => do nothing.
 *
 * This notification should send request to DB each 10 seconds (using teracota?) by default, or get latency count from config.
 * (probably is better to notify partner only when transaction was made? Make it as a trigger, not scheduler)
 *
 * Also should get not all transactions, only specific count. Max transaction count will be taken from config.
 *
 * Token:
 * Token is MD5 hash of next string: {payee wallet number} + {count of transactions} + String.format("%d:%d.%d%d", {amount sum of all transactions} + {MD5 password})
 * Dots and commas should be deleted from total amount. Password is MD5 hash, that is attached to the partner by elsom or partner itself.
 * */