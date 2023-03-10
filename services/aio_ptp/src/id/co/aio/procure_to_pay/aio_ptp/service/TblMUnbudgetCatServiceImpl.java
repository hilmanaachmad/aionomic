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

import id.co.aio.procure_to_pay.aio_ptp.TblMUnbudgetCat;
import id.co.aio.procure_to_pay.aio_ptp.TblTBudgetAdditional;
import id.co.aio.procure_to_pay.aio_ptp.TblTBudgetDetails;


/**
 * ServiceImpl object for domain model class TblMUnbudgetCat.
 *
 * @see TblMUnbudgetCat
 */
@Service("aio_ptp.TblMUnbudgetCatService")
@Validated
@EntityService(entityClass = TblMUnbudgetCat.class, serviceId = "aio_ptp")
public class TblMUnbudgetCatServiceImpl implements TblMUnbudgetCatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblMUnbudgetCatServiceImpl.class);

    @Lazy
    @Autowired
    @Qualifier("aio_ptp.TblTBudgetDetailsService")
    private TblTBudgetDetailsService tblTBudgetDetailsService;

    @Lazy
    @Autowired
    @Qualifier("aio_ptp.TblTBudgetAdditionalService")
    private TblTBudgetAdditionalService tblTBudgetAdditionalService;

    @Autowired
    @Qualifier("aio_ptp.TblMUnbudgetCatDao")
    private WMGenericDao<TblMUnbudgetCat, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TblMUnbudgetCat, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMUnbudgetCat create(TblMUnbudgetCat tblMunbudgetCat) {
        LOGGER.debug("Creating a new TblMUnbudgetCat with information: {}", tblMunbudgetCat);

        TblMUnbudgetCat tblMunbudgetCatCreated = this.wmGenericDao.create(tblMunbudgetCat);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tblMunbudgetCatCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblMUnbudgetCat getById(Integer tblmunbudgetcatId) {
        LOGGER.debug("Finding TblMUnbudgetCat by id: {}", tblmunbudgetcatId);
        return this.wmGenericDao.findById(tblmunbudgetcatId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblMUnbudgetCat findById(Integer tblmunbudgetcatId) {
        LOGGER.debug("Finding TblMUnbudgetCat by id: {}", tblmunbudgetcatId);
        try {
            return this.wmGenericDao.findById(tblmunbudgetcatId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TblMUnbudgetCat found with id: {}", tblmunbudgetcatId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<TblMUnbudgetCat> findByMultipleIds(List<Integer> tblmunbudgetcatIds, boolean orderedReturn) {
        LOGGER.debug("Finding TblMUnbudgetCats by ids: {}", tblmunbudgetcatIds);

        return this.wmGenericDao.findByMultipleIds(tblmunbudgetcatIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public TblMUnbudgetCat update(TblMUnbudgetCat tblMunbudgetCat) {
        LOGGER.debug("Updating TblMUnbudgetCat with information: {}", tblMunbudgetCat);

        this.wmGenericDao.update(tblMunbudgetCat);
        this.wmGenericDao.refresh(tblMunbudgetCat);

        return tblMunbudgetCat;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMUnbudgetCat partialUpdate(Integer tblmunbudgetcatId, Map<String, Object>tblMunbudgetCatPatch) {
        LOGGER.debug("Partially Updating the TblMUnbudgetCat with id: {}", tblmunbudgetcatId);

        TblMUnbudgetCat tblMunbudgetCat = getById(tblmunbudgetcatId);

        try {
            ObjectReader tblMunbudgetCatReader = this.objectMapper.reader().forType(TblMUnbudgetCat.class).withValueToUpdate(tblMunbudgetCat);
            tblMunbudgetCat = tblMunbudgetCatReader.readValue(this.objectMapper.writeValueAsString(tblMunbudgetCatPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tblMunbudgetCatPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tblMunbudgetCat = update(tblMunbudgetCat);

        return tblMunbudgetCat;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMUnbudgetCat delete(Integer tblmunbudgetcatId) {
        LOGGER.debug("Deleting TblMUnbudgetCat with id: {}", tblmunbudgetcatId);
        TblMUnbudgetCat deleted = this.wmGenericDao.findById(tblmunbudgetcatId);
        if (deleted == null) {
            LOGGER.debug("No TblMUnbudgetCat found with id: {}", tblmunbudgetcatId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TblMUnbudgetCat.class.getSimpleName(), tblmunbudgetcatId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(TblMUnbudgetCat tblMunbudgetCat) {
        LOGGER.debug("Deleting TblMUnbudgetCat with {}", tblMunbudgetCat);
        this.wmGenericDao.delete(tblMunbudgetCat);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblMUnbudgetCat> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TblMUnbudgetCats");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblMUnbudgetCat> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TblMUnbudgetCats");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblMUnbudgetCat to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblMUnbudgetCat to {} format", options.getExportType());
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
    public Page<TblTBudgetAdditional> findAssociatedTblTbudgetAdditionals(Integer ubCatId, Pageable pageable) {
        LOGGER.debug("Fetching all associated tblTbudgetAdditionals");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("tblMunbudgetCat.ubCatId = '" + ubCatId + "'");

        return tblTBudgetAdditionalService.findAll(queryBuilder.toString(), pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblTBudgetDetails> findAssociatedTblTbudgetDetailses(Integer ubCatId, Pageable pageable) {
        LOGGER.debug("Fetching all associated tblTbudgetDetailses");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("tblMunbudgetCat.ubCatId = '" + ubCatId + "'");

        return tblTBudgetDetailsService.findAll(queryBuilder.toString(), pageable);
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service TblTBudgetDetailsService instance
     */
    protected void setTblTBudgetDetailsService(TblTBudgetDetailsService service) {
        this.tblTBudgetDetailsService = service;
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service TblTBudgetAdditionalService instance
     */
    protected void setTblTBudgetAdditionalService(TblTBudgetAdditionalService service) {
        this.tblTBudgetAdditionalService = service;
    }

}