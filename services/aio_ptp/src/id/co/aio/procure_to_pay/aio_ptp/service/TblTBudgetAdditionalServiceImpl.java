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
import org.springframework.context.annotation.Lazy;
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

import id.co.aio.procure_to_pay.aio_ptp.TblTAdditionalAttachment;
import id.co.aio.procure_to_pay.aio_ptp.TblTAdditionalHistory;
import id.co.aio.procure_to_pay.aio_ptp.TblTBudgetAdditional;


/**
 * ServiceImpl object for domain model class TblTBudgetAdditional.
 *
 * @see TblTBudgetAdditional
 */
@Service("aio_ptp.TblTBudgetAdditionalService")
@Validated
@EntityService(entityClass = TblTBudgetAdditional.class, serviceId = "aio_ptp")
public class TblTBudgetAdditionalServiceImpl implements TblTBudgetAdditionalService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblTBudgetAdditionalServiceImpl.class);

    @Lazy
    @Autowired
    @Qualifier("aio_ptp.TblTAdditionalHistoryService")
    private TblTAdditionalHistoryService tblTAdditionalHistoryService;

    @Lazy
    @Autowired
    @Qualifier("aio_ptp.TblTAdditionalAttachmentService")
    private TblTAdditionalAttachmentService tblTAdditionalAttachmentService;

    @Autowired
    @Qualifier("aio_ptp.TblTBudgetAdditionalDao")
    private WMGenericDao<TblTBudgetAdditional, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TblTBudgetAdditional, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblTBudgetAdditional create(TblTBudgetAdditional tblTbudgetAdditional) {
        LOGGER.debug("Creating a new TblTBudgetAdditional with information: {}", tblTbudgetAdditional);

        TblTBudgetAdditional tblTbudgetAdditionalCreated = this.wmGenericDao.create(tblTbudgetAdditional);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tblTbudgetAdditionalCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblTBudgetAdditional getById(Integer tbltbudgetadditionalId) {
        LOGGER.debug("Finding TblTBudgetAdditional by id: {}", tbltbudgetadditionalId);
        return this.wmGenericDao.findById(tbltbudgetadditionalId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblTBudgetAdditional findById(Integer tbltbudgetadditionalId) {
        LOGGER.debug("Finding TblTBudgetAdditional by id: {}", tbltbudgetadditionalId);
        try {
            return this.wmGenericDao.findById(tbltbudgetadditionalId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TblTBudgetAdditional found with id: {}", tbltbudgetadditionalId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<TblTBudgetAdditional> findByMultipleIds(List<Integer> tbltbudgetadditionalIds, boolean orderedReturn) {
        LOGGER.debug("Finding TblTBudgetAdditionals by ids: {}", tbltbudgetadditionalIds);

        return this.wmGenericDao.findByMultipleIds(tbltbudgetadditionalIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public TblTBudgetAdditional update(TblTBudgetAdditional tblTbudgetAdditional) {
        LOGGER.debug("Updating TblTBudgetAdditional with information: {}", tblTbudgetAdditional);

        this.wmGenericDao.update(tblTbudgetAdditional);
        this.wmGenericDao.refresh(tblTbudgetAdditional);

        return tblTbudgetAdditional;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblTBudgetAdditional partialUpdate(Integer tbltbudgetadditionalId, Map<String, Object>tblTbudgetAdditionalPatch) {
        LOGGER.debug("Partially Updating the TblTBudgetAdditional with id: {}", tbltbudgetadditionalId);

        TblTBudgetAdditional tblTbudgetAdditional = getById(tbltbudgetadditionalId);

        try {
            ObjectReader tblTbudgetAdditionalReader = this.objectMapper.reader().forType(TblTBudgetAdditional.class).withValueToUpdate(tblTbudgetAdditional);
            tblTbudgetAdditional = tblTbudgetAdditionalReader.readValue(this.objectMapper.writeValueAsString(tblTbudgetAdditionalPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tblTbudgetAdditionalPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tblTbudgetAdditional = update(tblTbudgetAdditional);

        return tblTbudgetAdditional;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblTBudgetAdditional delete(Integer tbltbudgetadditionalId) {
        LOGGER.debug("Deleting TblTBudgetAdditional with id: {}", tbltbudgetadditionalId);
        TblTBudgetAdditional deleted = this.wmGenericDao.findById(tbltbudgetadditionalId);
        if (deleted == null) {
            LOGGER.debug("No TblTBudgetAdditional found with id: {}", tbltbudgetadditionalId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TblTBudgetAdditional.class.getSimpleName(), tbltbudgetadditionalId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(TblTBudgetAdditional tblTbudgetAdditional) {
        LOGGER.debug("Deleting TblTBudgetAdditional with {}", tblTbudgetAdditional);
        this.wmGenericDao.delete(tblTbudgetAdditional);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblTBudgetAdditional> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TblTBudgetAdditionals");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblTBudgetAdditional> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TblTBudgetAdditionals");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblTBudgetAdditional to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblTBudgetAdditional to {} format", options.getExportType());
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

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblTAdditionalAttachment> findAssociatedTblTadditionalAttachments(Integer baId, Pageable pageable) {
        LOGGER.debug("Fetching all associated tblTadditionalAttachments");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("tblTbudgetAdditional.baId = '" + baId + "'");

        return tblTAdditionalAttachmentService.findAll(queryBuilder.toString(), pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblTAdditionalHistory> findAssociatedTblTadditionalHistories(Integer baId, Pageable pageable) {
        LOGGER.debug("Fetching all associated tblTadditionalHistories");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("tblTbudgetAdditional.baId = '" + baId + "'");

        return tblTAdditionalHistoryService.findAll(queryBuilder.toString(), pageable);
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service TblTAdditionalHistoryService instance
     */
    protected void setTblTAdditionalHistoryService(TblTAdditionalHistoryService service) {
        this.tblTAdditionalHistoryService = service;
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service TblTAdditionalAttachmentService instance
     */
    protected void setTblTAdditionalAttachmentService(TblTAdditionalAttachmentService service) {
        this.tblTAdditionalAttachmentService = service;
    }

}