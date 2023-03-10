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

import id.co.aio.procure_to_pay.aio_ptp.TblTRfqVenQuotation;


/**
 * ServiceImpl object for domain model class TblTRfqVenQuotation.
 *
 * @see TblTRfqVenQuotation
 */
@Service("aio_ptp.TblTRfqVenQuotationService")
@Validated
@EntityService(entityClass = TblTRfqVenQuotation.class, serviceId = "aio_ptp")
public class TblTRfqVenQuotationServiceImpl implements TblTRfqVenQuotationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblTRfqVenQuotationServiceImpl.class);


    @Autowired
    @Qualifier("aio_ptp.TblTRfqVenQuotationDao")
    private WMGenericDao<TblTRfqVenQuotation, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TblTRfqVenQuotation, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblTRfqVenQuotation create(TblTRfqVenQuotation tblTrfqVenQuotation) {
        LOGGER.debug("Creating a new TblTRfqVenQuotation with information: {}", tblTrfqVenQuotation);

        TblTRfqVenQuotation tblTrfqVenQuotationCreated = this.wmGenericDao.create(tblTrfqVenQuotation);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tblTrfqVenQuotationCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblTRfqVenQuotation getById(Integer tbltrfqvenquotationId) {
        LOGGER.debug("Finding TblTRfqVenQuotation by id: {}", tbltrfqvenquotationId);
        return this.wmGenericDao.findById(tbltrfqvenquotationId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblTRfqVenQuotation findById(Integer tbltrfqvenquotationId) {
        LOGGER.debug("Finding TblTRfqVenQuotation by id: {}", tbltrfqvenquotationId);
        try {
            return this.wmGenericDao.findById(tbltrfqvenquotationId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TblTRfqVenQuotation found with id: {}", tbltrfqvenquotationId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<TblTRfqVenQuotation> findByMultipleIds(List<Integer> tbltrfqvenquotationIds, boolean orderedReturn) {
        LOGGER.debug("Finding TblTRfqVenQuotations by ids: {}", tbltrfqvenquotationIds);

        return this.wmGenericDao.findByMultipleIds(tbltrfqvenquotationIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public TblTRfqVenQuotation update(TblTRfqVenQuotation tblTrfqVenQuotation) {
        LOGGER.debug("Updating TblTRfqVenQuotation with information: {}", tblTrfqVenQuotation);

        this.wmGenericDao.update(tblTrfqVenQuotation);
        this.wmGenericDao.refresh(tblTrfqVenQuotation);

        return tblTrfqVenQuotation;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblTRfqVenQuotation partialUpdate(Integer tbltrfqvenquotationId, Map<String, Object>tblTrfqVenQuotationPatch) {
        LOGGER.debug("Partially Updating the TblTRfqVenQuotation with id: {}", tbltrfqvenquotationId);

        TblTRfqVenQuotation tblTrfqVenQuotation = getById(tbltrfqvenquotationId);

        try {
            ObjectReader tblTrfqVenQuotationReader = this.objectMapper.reader().forType(TblTRfqVenQuotation.class).withValueToUpdate(tblTrfqVenQuotation);
            tblTrfqVenQuotation = tblTrfqVenQuotationReader.readValue(this.objectMapper.writeValueAsString(tblTrfqVenQuotationPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tblTrfqVenQuotationPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tblTrfqVenQuotation = update(tblTrfqVenQuotation);

        return tblTrfqVenQuotation;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblTRfqVenQuotation delete(Integer tbltrfqvenquotationId) {
        LOGGER.debug("Deleting TblTRfqVenQuotation with id: {}", tbltrfqvenquotationId);
        TblTRfqVenQuotation deleted = this.wmGenericDao.findById(tbltrfqvenquotationId);
        if (deleted == null) {
            LOGGER.debug("No TblTRfqVenQuotation found with id: {}", tbltrfqvenquotationId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TblTRfqVenQuotation.class.getSimpleName(), tbltrfqvenquotationId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(TblTRfqVenQuotation tblTrfqVenQuotation) {
        LOGGER.debug("Deleting TblTRfqVenQuotation with {}", tblTrfqVenQuotation);
        this.wmGenericDao.delete(tblTrfqVenQuotation);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblTRfqVenQuotation> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TblTRfqVenQuotations");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblTRfqVenQuotation> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TblTRfqVenQuotations");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblTRfqVenQuotation to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblTRfqVenQuotation to {} format", options.getExportType());
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