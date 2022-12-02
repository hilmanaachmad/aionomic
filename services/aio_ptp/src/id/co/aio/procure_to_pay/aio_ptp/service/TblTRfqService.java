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

import id.co.aio.procure_to_pay.aio_ptp.TblTRfq;
import id.co.aio.procure_to_pay.aio_ptp.TblTRfqVendor;

/**
 * Service object for domain model class {@link TblTRfq}.
 */
public interface TblTRfqService {

    /**
     * Creates a new TblTRfq. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblTRfq if any.
     *
     * @param tblTrfq Details of the TblTRfq to be created; value cannot be null.
     * @return The newly created TblTRfq.
     */
    TblTRfq create(@Valid TblTRfq tblTrfq);


	/**
     * Returns TblTRfq by given id if exists.
     *
     * @param tbltrfqId The id of the TblTRfq to get; value cannot be null.
     * @return TblTRfq associated with the given tbltrfqId.
	 * @throws EntityNotFoundException If no TblTRfq is found.
     */
    TblTRfq getById(Integer tbltrfqId);

    /**
     * Find and return the TblTRfq by given id if exists, returns null otherwise.
     *
     * @param tbltrfqId The id of the TblTRfq to get; value cannot be null.
     * @return TblTRfq associated with the given tbltrfqId.
     */
    TblTRfq findById(Integer tbltrfqId);

	/**
     * Find and return the list of TblTRfqs by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param tbltrfqIds The id's of the TblTRfq to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return TblTRfqs associated with the given tbltrfqIds.
     */
    List<TblTRfq> findByMultipleIds(List<Integer> tbltrfqIds, boolean orderedReturn);


    /**
     * Updates the details of an existing TblTRfq. It replaces all fields of the existing TblTRfq with the given tblTrfq.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblTRfq if any.
     *
     * @param tblTrfq The details of the TblTRfq to be updated; value cannot be null.
     * @return The updated TblTRfq.
     * @throws EntityNotFoundException if no TblTRfq is found with given input.
     */
    TblTRfq update(@Valid TblTRfq tblTrfq);


    /**
     * Partially updates the details of an existing TblTRfq. It updates only the
     * fields of the existing TblTRfq which are passed in the tblTrfqPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblTRfq if any.
     *
     * @param tbltrfqId The id of the TblTRfq to be deleted; value cannot be null.
     * @param tblTrfqPatch The partial data of TblTRfq which is supposed to be updated; value cannot be null.
     * @return The updated TblTRfq.
     * @throws EntityNotFoundException if no TblTRfq is found with given input.
     */
    TblTRfq partialUpdate(Integer tbltrfqId, Map<String, Object> tblTrfqPatch);

    /**
     * Deletes an existing TblTRfq with the given id.
     *
     * @param tbltrfqId The id of the TblTRfq to be deleted; value cannot be null.
     * @return The deleted TblTRfq.
     * @throws EntityNotFoundException if no TblTRfq found with the given id.
     */
    TblTRfq delete(Integer tbltrfqId);

    /**
     * Deletes an existing TblTRfq with the given object.
     *
     * @param tblTrfq The instance of the TblTRfq to be deleted; value cannot be null.
     */
    void delete(TblTRfq tblTrfq);

    /**
     * Find all TblTRfqs matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TblTRfqs.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<TblTRfq> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all TblTRfqs matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TblTRfqs.
     *
     * @see Pageable
     * @see Page
     */
    Page<TblTRfq> findAll(String query, Pageable pageable);

    /**
     * Exports all TblTRfqs matching the given input query to the given exportType format.
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
     * Exports all TblTRfqs matching the given input query to the given exportType format.
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
     * Retrieve the count of the TblTRfqs in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the TblTRfq.
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
     * Returns the associated tblTrfqVendors for given TblTRfq id.
     *
     * @param rfqId value of rfqId; value cannot be null
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of associated TblTRfqVendor instances.
     *
     * @see Pageable
     * @see Page
     */
    Page<TblTRfqVendor> findAssociatedTblTrfqVendors(Integer rfqId, Pageable pageable);

}