/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import id.co.aio.procure_to_pay.aio_ptp.TblMCompany;
import id.co.aio.procure_to_pay.aio_ptp.TblMIo;
import id.co.aio.procure_to_pay.aio_ptp.TblTBudgetHeader;
import id.co.aio.procure_to_pay.aio_ptp.TblTPr;

/**
 * Service object for domain model class {@link TblMCompany}.
 */
public interface TblMCompanyService {

    /**
     * Creates a new TblMCompany. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblMCompany if any.
     *
     * @param tblMcompany Details of the TblMCompany to be created; value cannot be null.
     * @return The newly created TblMCompany.
     */
    TblMCompany create(@Valid TblMCompany tblMcompany);


	/**
     * Returns TblMCompany by given id if exists.
     *
     * @param tblmcompanyId The id of the TblMCompany to get; value cannot be null.
     * @return TblMCompany associated with the given tblmcompanyId.
	 * @throws EntityNotFoundException If no TblMCompany is found.
     */
    TblMCompany getById(Integer tblmcompanyId);

    /**
     * Find and return the TblMCompany by given id if exists, returns null otherwise.
     *
     * @param tblmcompanyId The id of the TblMCompany to get; value cannot be null.
     * @return TblMCompany associated with the given tblmcompanyId.
     */
    TblMCompany findById(Integer tblmcompanyId);

	/**
     * Find and return the list of TblMCompanies by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param tblmcompanyIds The id's of the TblMCompany to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return TblMCompanies associated with the given tblmcompanyIds.
     */
    List<TblMCompany> findByMultipleIds(List<Integer> tblmcompanyIds, boolean orderedReturn);


    /**
     * Updates the details of an existing TblMCompany. It replaces all fields of the existing TblMCompany with the given tblMcompany.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblMCompany if any.
     *
     * @param tblMcompany The details of the TblMCompany to be updated; value cannot be null.
     * @return The updated TblMCompany.
     * @throws EntityNotFoundException if no TblMCompany is found with given input.
     */
    TblMCompany update(@Valid TblMCompany tblMcompany);


    /**
     * Partially updates the details of an existing TblMCompany. It updates only the
     * fields of the existing TblMCompany which are passed in the tblMcompanyPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblMCompany if any.
     *
     * @param tblmcompanyId The id of the TblMCompany to be deleted; value cannot be null.
     * @param tblMcompanyPatch The partial data of TblMCompany which is supposed to be updated; value cannot be null.
     * @return The updated TblMCompany.
     * @throws EntityNotFoundException if no TblMCompany is found with given input.
     */
    TblMCompany partialUpdate(Integer tblmcompanyId, Map<String, Object> tblMcompanyPatch);

    /**
     * Deletes an existing TblMCompany with the given id.
     *
     * @param tblmcompanyId The id of the TblMCompany to be deleted; value cannot be null.
     * @return The deleted TblMCompany.
     * @throws EntityNotFoundException if no TblMCompany found with the given id.
     */
    TblMCompany delete(Integer tblmcompanyId);

    /**
     * Deletes an existing TblMCompany with the given object.
     *
     * @param tblMcompany The instance of the TblMCompany to be deleted; value cannot be null.
     */
    void delete(TblMCompany tblMcompany);

    /**
     * Find all TblMCompanies matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TblMCompanies.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<TblMCompany> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all TblMCompanies matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TblMCompanies.
     *
     * @see Pageable
     * @see Page
     */
    Page<TblMCompany> findAll(String query, Pageable pageable);

    /**
     * Exports all TblMCompanies matching the given input query to the given exportType format.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param exportType The format in which to export the data; value cannot be null.
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return The Downloadable file in given export type.
     *
     * @see Pageable
     * @see ExportType
     * @see Downloadable
     */
    Downloadable export(ExportType exportType, String query, Pageable pageable);

    /**
     * Exports all TblMCompanies matching the given input query to the given exportType format.
     *
     * @param options The export options provided by the user; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @param outputStream The output stream of the file for the exported data to be written to.
     *
     * @see DataExportOptions
     * @see Pageable
     * @see OutputStream
     */
    void export(DataExportOptions options, Pageable pageable, OutputStream outputStream);

    /**
     * Retrieve the count of the TblMCompanies in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the TblMCompany.
     */
    long count(String query);

    /**
     * Retrieve aggregated values with matching aggregation info.
     *
     * @param aggregationInfo info related to aggregations.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return Paginated data with included fields.
     *
     * @see AggregationInfo
     * @see Pageable
     * @see Page
	 */
    Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable);

    /*
     * Returns the associated tblMios for given TblMCompany id.
     *
     * @param cid value of cid; value cannot be null
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of associated TblMIo instances.
     *
     * @see Pageable
     * @see Page
     */
    Page<TblMIo> findAssociatedTblMios(Integer cid, Pageable pageable);

    /*
     * Returns the associated tblTbudgetHeaders for given TblMCompany id.
     *
     * @param cid value of cid; value cannot be null
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of associated TblTBudgetHeader instances.
     *
     * @see Pageable
     * @see Page
     */
    Page<TblTBudgetHeader> findAssociatedTblTbudgetHeaders(Integer cid, Pageable pageable);

    /*
     * Returns the associated tblTprs for given TblMCompany id.
     *
     * @param cid value of cid; value cannot be null
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of associated TblTPr instances.
     *
     * @see Pageable
     * @see Page
     */
    Page<TblTPr> findAssociatedTblTprs(Integer cid, Pageable pageable);

}