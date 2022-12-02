/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.wavemaker.commons.InvalidInputException;
import com.wavemaker.commons.MessageResource;
import com.wavemaker.runtime.data.annotations.EntityService;
import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import id.co.aio.procure_to_pay.aio_ptp.TblTReclassHistory;


/**
 * ServiceImpl object for domain model class TblTReclassHistory.
 *
 * @see TblTReclassHistory
 */
@Service("aio_ptp.TblTReclassHistoryService")
@Validated
@EntityService(entityClass = TblTReclassHistory.class, serviceId = "aio_ptp")
public class TblTReclassHistoryServiceImpl implements TblTReclassHistoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblTReclassHistoryServiceImpl.class);


    @Autowired
    @Qualifier("aio_ptp.TblTReclassHistoryDao")
    private WMGenericDao<TblTReclassHistory, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TblTReclassHistory, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblTReclassHistory create(TblTReclassHistory tblTreclassHistory) {
        LOGGER.debug("Creating a new TblTReclassHistory with information: {}", tblTreclassHistory);

        TblTReclassHistory tblTreclassHistoryCreated = this.wmGenericDao.create(tblTreclassHistory);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tblTreclassHistoryCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblTReclassHistory getById(Integer tbltreclasshistoryId) {
        LOGGER.debug("Finding TblTReclassHistory by id: {}", tbltreclasshistoryId);
        return this.wmGenericDao.findById(tbltreclasshistoryId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblTReclassHistory findById(Integer tbltreclasshistoryId) {
        LOGGER.debug("Finding TblTReclassHistory by id: {}", tbltreclasshistoryId);
        try {
            return this.wmGenericDao.findById(tbltreclasshistoryId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TblTReclassHistory found with id: {}", tbltreclasshistoryId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<TblTReclassHistory> findByMultipleIds(List<Integer> tbltreclasshistoryIds, boolean orderedReturn) {
        LOGGER.debug("Finding TblTReclassHistories by ids: {}", tbltreclasshistoryIds);

        return this.wmGenericDao.findByMultipleIds(tbltreclasshistoryIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public TblTReclassHistory update(TblTReclassHistory tblTreclassHistory) {
        LOGGER.debug("Updating TblTReclassHistory with information: {}", tblTreclassHistory);

        this.wmGenericDao.update(tblTreclassHistory);
        this.wmGenericDao.refresh(tblTreclassHistory);

        return tblTreclassHistory;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblTReclassHistory partialUpdate(Integer tbltreclasshistoryId, Map<String, Object>tblTreclassHistoryPatch) {
        LOGGER.debug("Partially Updating the TblTReclassHistory with id: {}", tbltreclasshistoryId);

        TblTReclassHistory tblTreclassHistory = getById(tbltreclasshistoryId);

        try {
            ObjectReader tblTreclassHistoryReader = this.objectMapper.reader().forType(TblTReclassHistory.class).withValueToUpdate(tblTreclassHistory);
            tblTreclassHistory = tblTreclassHistoryReader.readValue(this.objectMapper.writeValueAsString(tblTreclassHistoryPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tblTreclassHistoryPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tblTreclassHistory = update(tblTreclassHistory);

        return tblTreclassHistory;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblTReclassHistory delete(Integer tbltreclasshistoryId) {
        LOGGER.debug("Deleting TblTReclassHistory with id: {}", tbltreclasshistoryId);
        TblTReclassHistory deleted = this.wmGenericDao.findById(tbltreclasshistoryId);
        if (deleted == null) {
            LOGGER.debug("No TblTReclassHistory found with id: {}", tbltreclasshistoryId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TblTReclassHistory.class.getSimpleName(), tbltreclasshistoryId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(TblTReclassHistory tblTreclassHistory) {
        LOGGER.debug("Deleting TblTReclassHistory with {}", tblTreclassHistory);
        this.wmGenericDao.delete(tblTreclassHistory);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblTReclassHistory> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TblTReclassHistories");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblTReclassHistory> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TblTReclassHistories");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblTReclassHistory to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblTReclassHistory to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }



}