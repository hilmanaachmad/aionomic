/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.service;

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

import id.co.aio.procure_to_pay.avm2.TlMsLocNew;

/**
 * Service object for domain model class {@link TlMsLocNew}.
 */
public interface TlMsLocNewService {

    /**
     * Creates a new TlMsLocNew. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlMsLocNew if any.
     *
     * @param tlMsLocNew Details of the TlMsLocNew to be created; value cannot be null.
     * @return The newly created TlMsLocNew.
     */
    TlMsLocNew create(@Valid TlMsLocNew tlMsLocNew);


	/**
     * Returns TlMsLocNew by given id if exists.
     *
     * @param tlmslocnewId The id of the TlMsLocNew to get; value cannot be null.
     * @return TlMsLocNew associated with the given tlmslocnewId.
	 * @throws EntityNotFoundException If no TlMsLocNew is found.
     */
    TlMsLocNew getById(Integer tlmslocnewId);

    /**
     * Find and return the TlMsLocNew by given id if exists, returns null otherwise.
     *
     * @param tlmslocnewId The id of the TlMsLocNew to get; value cannot be null.
     * @return TlMsLocNew associated with the given tlmslocnewId.
     */
    TlMsLocNew findById(Integer tlmslocnewId);

	/**
     * Find and return the list of TlMsLocNews by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param tlmslocnewIds The id's of the TlMsLocNew to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return TlMsLocNews associated with the given tlmslocnewIds.
     */
    List<TlMsLocNew> findByMultipleIds(List<Integer> tlmslocnewIds, boolean orderedReturn);


    /**
     * Updates the details of an existing TlMsLocNew. It replaces all fields of the existing TlMsLocNew with the given tlMsLocNew.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlMsLocNew if any.
     *
     * @param tlMsLocNew The details of the TlMsLocNew to be updated; value cannot be null.
     * @return The updated TlMsLocNew.
     * @throws EntityNotFoundException if no TlMsLocNew is found with given input.
     */
    TlMsLocNew update(@Valid TlMsLocNew tlMsLocNew);


    /**
     * Partially updates the details of an existing TlMsLocNew. It updates only the
     * fields of the existing TlMsLocNew which are passed in the tlMsLocNewPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlMsLocNew if any.
     *
     * @param tlmslocnewId The id of the TlMsLocNew to be deleted; value cannot be null.
     * @param tlMsLocNewPatch The partial data of TlMsLocNew which is supposed to be updated; value cannot be null.
     * @return The updated TlMsLocNew.
     * @throws EntityNotFoundException if no TlMsLocNew is found with given input.
     */
    TlMsLocNew partialUpdate(Integer tlmslocnewId, Map<String, Object> tlMsLocNewPatch);

    /**
     * Deletes an existing TlMsLocNew with the given id.
     *
     * @param tlmslocnewId The id of the TlMsLocNew to be deleted; value cannot be null.
     * @return The deleted TlMsLocNew.
     * @throws EntityNotFoundException if no TlMsLocNew found with the given id.
     */
    TlMsLocNew delete(Integer tlmslocnewId);

    /**
     * Deletes an existing TlMsLocNew with the given object.
     *
     * @param tlMsLocNew The instance of the TlMsLocNew to be deleted; value cannot be null.
     */
    void delete(TlMsLocNew tlMsLocNew);

    /**
     * Find all TlMsLocNews matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TlMsLocNews.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<TlMsLocNew> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all TlMsLocNews matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TlMsLocNews.
     *
     * @see Pageable
     * @see Page
     */
    Page<TlMsLocNew> findAll(String query, Pageable pageable);

    /**
     * Exports all TlMsLocNews matching the given input query to the given exportType format.
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
     * Exports all TlMsLocNews matching the given input query to the given exportType format.
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
     * Retrieve the count of the TlMsLocNews in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the TlMsLocNew.
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


}