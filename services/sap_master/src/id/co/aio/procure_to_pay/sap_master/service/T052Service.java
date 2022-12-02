/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.sap_master.service;

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

import id.co.aio.procure_to_pay.sap_master.T052;
import id.co.aio.procure_to_pay.sap_master.T052Id;

/**
 * Service object for domain model class {@link T052}.
 */
public interface T052Service {

    /**
     * Creates a new T052. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on T052 if any.
     *
     * @param t052 Details of the T052 to be created; value cannot be null.
     * @return The newly created T052.
     */
    T052 create(@Valid T052 t052);


	/**
     * Returns T052 by given id if exists.
     *
     * @param t052Id The id of the T052 to get; value cannot be null.
     * @return T052 associated with the given t052Id.
	 * @throws EntityNotFoundException If no T052 is found.
     */
    T052 getById(T052Id t052Id);

    /**
     * Find and return the T052 by given id if exists, returns null otherwise.
     *
     * @param t052Id The id of the T052 to get; value cannot be null.
     * @return T052 associated with the given t052Id.
     */
    T052 findById(T052Id t052Id);

	/**
     * Find and return the list of T052s by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param t052Ids The id's of the T052 to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return T052s associated with the given t052Ids.
     */
    List<T052> findByMultipleIds(List<T052Id> t052Ids, boolean orderedReturn);


    /**
     * Updates the details of an existing T052. It replaces all fields of the existing T052 with the given t052.
     *
     * This method overrides the input field values using Server side or database managed properties defined on T052 if any.
     *
     * @param t052 The details of the T052 to be updated; value cannot be null.
     * @return The updated T052.
     * @throws EntityNotFoundException if no T052 is found with given input.
     */
    T052 update(@Valid T052 t052);


    /**
     * Partially updates the details of an existing T052. It updates only the
     * fields of the existing T052 which are passed in the t052Patch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on T052 if any.
     *
     * @param t052Id The id of the T052 to be deleted; value cannot be null.
     * @param t052Patch The partial data of T052 which is supposed to be updated; value cannot be null.
     * @return The updated T052.
     * @throws EntityNotFoundException if no T052 is found with given input.
     */
    T052 partialUpdate(T052Id t052Id, Map<String, Object> t052Patch);

    /**
     * Deletes an existing T052 with the given id.
     *
     * @param t052Id The id of the T052 to be deleted; value cannot be null.
     * @return The deleted T052.
     * @throws EntityNotFoundException if no T052 found with the given id.
     */
    T052 delete(T052Id t052Id);

    /**
     * Deletes an existing T052 with the given object.
     *
     * @param t052 The instance of the T052 to be deleted; value cannot be null.
     */
    void delete(T052 t052);

    /**
     * Find all T052s matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching T052s.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<T052> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all T052s matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching T052s.
     *
     * @see Pageable
     * @see Page
     */
    Page<T052> findAll(String query, Pageable pageable);

    /**
     * Exports all T052s matching the given input query to the given exportType format.
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
     * Exports all T052s matching the given input query to the given exportType format.
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
     * Retrieve the count of the T052s in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the T052.
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